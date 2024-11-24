package main.java.com.example;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import main.java.com.example.Utilisateur;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Interface extends Application {
    private Map<String, Utilisateur> utilisateurs;
    private Map<String, Stage> fenetresUtilisateurs;

      /**
     * Initialise et affiche la fenêtre principale de l'application.
     *
     * @param primaryStage Le stage principal de l'application JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        utilisateurs = new HashMap<>();
        fenetresUtilisateurs = new HashMap<>();
        primaryStage.setTitle("MiniRSA Communication");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefRowCount(10);

        Button addUserButton = new Button("Ajouter un utilisateur");
        Button showUsersButton = new Button("Liste des utilisateurs");
        TextArea userListArea = new TextArea();
        userListArea.setEditable(false);
        userListArea.setPrefRowCount(5);

        addUserButton.setOnAction(e -> showAddUserDialog(logArea));
        showUsersButton.setOnAction(e -> {
            userListArea.clear();
            utilisateurs.keySet().forEach(username -> userListArea.appendText(username + "\n"));
        });

        mainLayout.getChildren().addAll(
            new Label("Logs des opérations:"),
            logArea,
            addUserButton,
            showUsersButton,
            new Label("Utilisateurs connectés:"),
            userListArea
        );

        Scene scene = new Scene(mainLayout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Affiche une boîte de dialogue pour ajouter un nouvel utilisateur.
     *
     * @param logArea La zone de texte pour afficher les logs.
     */
    private void showAddUserDialog(TextArea logArea) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Nouvel utilisateur");
        dialog.setHeaderText("Entrez le nom de l'utilisateur");

        ButtonType buttonTypeOk = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nom d'utilisateur");
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(username, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return username.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(userName -> {
            if (!userName.isEmpty() && !utilisateurs.containsKey(userName)) {
                createUserWindow(userName, logArea);
            } else {
                showAlert("Erreur", "Nom d'utilisateur invalide ou déjà utilisé");
            }
        });
    }

     /**
     * Crée une nouvelle fenêtre pour un utilisateur.
     *
     * @param username Le nom de l'utilisateur.
     * @param logArea La zone de texte pour afficher les logs.
     */
    private void createUserWindow(String username, TextArea logArea) {
        Stage userStage = new Stage();
        userStage.setTitle("Utilisateur: " + username);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setPrefRowCount(10);

        ComboBox<String> recipientBox = new ComboBox<>();
        recipientBox.setPromptText("Destinataire");
        updateRecipientList(recipientBox, username);

        TextField messageField = new TextField();
        messageField.setPromptText("Message");

        Button sendButton = new Button("Envoyer");

        // Créer l'utilisateur et son thread
        Utilisateur user = new Utilisateur(username, messageArea);
        utilisateurs.put(username, user);
        fenetresUtilisateurs.put(username, userStage);
        user.start();

        // Log de la création de l'utilisateur et de ses clés
        logArea.appendText("=== Création de l'utilisateur " + username + " ===\n");
        logArea.appendText("Génération des clés RSA...\n");
        logArea.appendText("Clé publique: " + user.getKeyPair().getPublicKey() + "\n");
        logArea.appendText("Modulo: " + user.getKeyPair().getModulus() + "\n\n");

        sendButton.setOnAction(e -> {
            String recipient = recipientBox.getValue();
            String message = messageField.getText();
            if (recipient != null && !message.isEmpty()) {
                Utilisateur destinataire = utilisateurs.get(recipient);
                if (destinataire != null) {
                    // Log des étapes de chiffrement et signature
                    logArea.appendText("=== Envoi de message de " + username + " à " + recipient + " ===\n");
                    logArea.appendText("1. Message original: " + message + "\n");
                    logArea.appendText("2. Calcul de l'empreinte...\n");
                    logArea.appendText("3. Signature du message...\n");
                    logArea.appendText("4. Chiffrement du message...\n");
                    logArea.appendText("5. Message envoyé\n\n");

                    user.envoyerMessage(message, destinataire);
                    messageField.clear();
                }
            }
        });

        layout.getChildren().addAll(
            new Label("Messages:"),
            messageArea,
            new Label("Nouveau message:"),
            recipientBox,
            messageField,
            sendButton
        );

        Scene scene = new Scene(layout, 300, 400);
        userStage.setScene(scene);
        userStage.show();

        userStage.setOnCloseRequest(e -> {
            user.arreter();
            utilisateurs.remove(username);
            fenetresUtilisateurs.remove(username);
            updateAllRecipientLists();
            logArea.appendText("=== Déconnexion de " + username + " ===\n\n");
        });
    }
    

    
    /**
     * Affiche une alerte 
     *
     * @param title Le titre de l'alerte.
     * @param content Le contenu de l'alerte.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Met à jour la liste des destinataires possibles pour un utilisateur.
     *
     * @param recipientBox La ComboBox contenant la liste des destinataires.
     * @param currentUser Le nom de l'utilisateur actuel.
     */
    private void updateRecipientList(ComboBox<String> recipientBox, String currentUser) {
        recipientBox.getItems().clear();
        for (String user : utilisateurs.keySet()) {
            if (!user.equals(currentUser)) {
                recipientBox.getItems().add(user);
            }
        }
    }
    
     /**
     * Met à jour les listes de destinataires pour tous les utilisateurs.
     */
    private void updateAllRecipientLists() {
        for (Stage stage : fenetresUtilisateurs.values()) {
            VBox layout = (VBox) stage.getScene().getRoot();
            ComboBox<String> recipientBox = (ComboBox<String>) layout.getChildren().get(3);
            String currentUser = stage.getTitle().substring(12); 
            updateRecipientList(recipientBox, currentUser);
        }
    }
    
    
}
