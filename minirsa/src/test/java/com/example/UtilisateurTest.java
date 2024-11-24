package test.java.com.example;// Correction du package selon l'arborescence

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.embed.swing.JFXPanel;

import main.java.com.example.Utilisateur;
import main.java.com.example.RSAKeyPair;
import main.java.com.example.Message;

public class UtilisateurTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); 
    }

    @Test
    public void TestCreationUtilisateur() {
        CreationUtilisateurTest("Alice");
        CreationUtilisateurTest("");
    }

    @Test
    public void TestEnvoiMessage() {
        EnvoiMessageTest("Alice", "Bob", "Hello Bob!");
        EnvoiMessageTest("Alice", "Bob", "");
    }

    @Test
    public void TestReceptionMessage() {
        ReceptionMessageTest("Alice", "Bob", "Hello Alice!");
    }

    public void CreationUtilisateurTest(String nom) {
        Utilisateur utilisateur = new Utilisateur(nom, null);
        assertNotNull(utilisateur);
        assertEquals(nom, utilisateur.getNom());
        assertNotNull(utilisateur.getKeyPair());
    }

    public void EnvoiMessageTest(String expediteur, String destinataire, String message) {
        Utilisateur exp = new Utilisateur(expediteur, null);
        Utilisateur dest = new Utilisateur(destinataire, null);
        exp.envoyerMessage(message, dest);
        assertNotNull(exp);
        assertNotNull(dest);
    }

    public void ReceptionMessageTest(String destinataire, String expediteur, String message) {
        Utilisateur dest = new Utilisateur(destinataire, null);
        Utilisateur exp = new Utilisateur(expediteur, null);
        Message msg = new Message(exp, exp.getKeyPair().getPublicKey(), exp.getKeyPair().getPrivateKey());
        dest.recevoirMessage(msg);
        assertNotNull(dest);
    }
}
