package main.java.com.example;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.HashMap;
import java.util.Map;

public class Utilisateur extends Thread {
    private String nom;
    private RSAKeyPair keyPair;
    private TextArea messageArea;
    private BlockingQueue<Message> messagesRecus;
    private boolean running;
    private boolean certificatVerified;
    private String certificat;
    private Map<String, String> certificatsVerifies; // Dictionnaire pour stocker les certificats vérifiés

    public Utilisateur(String nom, TextArea messageArea) {
        this.nom = nom;
        this.keyPair = RSA.genererCle(1024);
        this.messagesRecus = new LinkedBlockingQueue<>();
        this.messageArea = messageArea;
        this.running = true;
        this.certificatVerified = false;
        this.certificat = Certificat.genererCertificat(this.nom, this.keyPair.getPublicKey(),this.keyPair.getModulus());
        this.certificatsVerifies = new HashMap<>(); // Initialisation du dictionnaire
    }

    public String getNom() {
        return this.nom;
    }

    public RSAKeyPair getKeyPair() {
        return keyPair;
    }

    public String getCertificat() {
        return certificat;
    }

    public boolean isCertificatVerified() {
        return certificatVerified;
    }

    public void setCertificatVerified(boolean certificatVerified) {
        this.certificatVerified = certificatVerified;
    }

    public void ajouterCertificatVerifie(String nomUtilisateur, String certificat) {
        certificatsVerifies.put(nomUtilisateur, certificat);
    }

    public String getCertificatVerifie(String nomUtilisateur) {
        return certificatsVerifies.get(nomUtilisateur);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Message message = messagesRecus.take();
                traiterMessage(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void envoyerMessage(String contenu, Utilisateur destinataire) {
        try {
            BigInteger messageChiffre = RsaTextConverter.chiffrer(contenu, 
                destinataire.getKeyPair().getPublicKey(),
                destinataire.getKeyPair().getModulus());
            BigInteger signature = Signature.signer(contenu, 
                this.keyPair.getPrivateKey(), 
                this.keyPair.getModulus());
            Message message = new Message(this, messageChiffre, signature);
            destinataire.recevoirMessage(message);
            
            if (messageArea != null) {
                Platform.runLater(() -> messageArea.appendText("Message envoyé à " + destinataire.getNom() + "\n"));
            }
        } catch (Exception e) {
            if (messageArea != null) {
                Platform.runLater(() -> messageArea.appendText("Erreur d'envoi: " + e.getMessage() + "\n"));
            }
        }
    }

    public void traiterMessage(Message message) {
        try {
            String messageDecrypte = RsaTextConverter.dechiffrer(message.getContenuChiffre(), this.getKeyPair().getPrivateKey(), this.getKeyPair().getModulus());
            boolean signatureValide = Signature.verifier(messageDecrypte, message.getSignature(), message.getExpediteur().getKeyPair().getPublicKey(), message.getExpediteur().getKeyPair().getModulus());
            Platform.runLater(() -> {
                messageArea.appendText("\nMessage reçu de " + message.getExpediteur().getNom() + ":\n");
                messageArea.appendText("Contenu: " + messageDecrypte + "\n");
                messageArea.appendText("Signature valide: " + signatureValide + "\n");
            });
        } catch (Exception e) {
            Platform.runLater(() -> 
                messageArea.appendText("Erreur de traitement: " + e.getMessage() + "\n")
            );
        }
    }

    public void recevoirMessage(Message message) {
        messagesRecus.offer(message);
    }

    public void arreter() {
        running = false;
        interrupt();
    }
}