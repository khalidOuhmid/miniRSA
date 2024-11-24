package main.java.com.example;

import java.math.BigInteger;

public class Message {
    private Utilisateur expediteur;
    private BigInteger contenuChiffre;
    private BigInteger signature;

    public Message(Utilisateur expediteur, BigInteger contenuChiffre, BigInteger signature) {
        this.expediteur = expediteur;
        this.contenuChiffre = contenuChiffre;
        this.signature = signature;
    }

    // Getters
    public Utilisateur getExpediteur() {
        return expediteur;
    }

    public BigInteger getContenuChiffre() {
        return contenuChiffre;
    }

    public BigInteger getSignature() {
        return signature;
    }
}
