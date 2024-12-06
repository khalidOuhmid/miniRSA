    package test.java.com.example;

    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.*;
    import javafx.embed.swing.JFXPanel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.scene.Node;
    import main.java.com.example.Utilisateur;
    import main.java.com.example.RSAKeyPair;
    import main.java.com.example.Message;   

    public class UtilisateurTest {


        @Test
        public void testCreationUtilisateur() {
            creationUtilisateurTest("Alice");
            creationUtilisateurTest("");
        }

        @Test
        public void testEnvoiMessage() {
            envoiMessageTest("Alice", "Bob", "Hello Bob!");
            envoiMessageTest("Alice", "Bob", "");
        }

        @Test
        public void testReceptionMessage() {
            receptionMessageTest("Alice", "Bob", "Hello Alice!");
        }

        private void creationUtilisateurTest(String nom) {
            Utilisateur utilisateur = new Utilisateur(nom, null);
            assertNotNull(utilisateur);
            assertEquals(nom, utilisateur.getNom());
            assertNotNull(utilisateur.getKeyPair());
        }

        private void envoiMessageTest(String expediteur, String destinataire, String message) {
            Utilisateur exp = new Utilisateur(expediteur, null);
            Utilisateur dest = new Utilisateur(destinataire, null);
            exp.envoyerMessage(message, dest);
            assertNotNull(exp);
            assertNotNull(dest);
        }

        private void receptionMessageTest(String destinataire, String expediteur, String message) {
            Utilisateur dest = new Utilisateur(destinataire, null);
            Utilisateur exp = new Utilisateur(expediteur, null);
            Message msg = new Message(exp, exp.getKeyPair().getPublicKey(), exp.getKeyPair().getPrivateKey());
            dest.recevoirMessage(msg);
            assertNotNull(dest);
        }
    }