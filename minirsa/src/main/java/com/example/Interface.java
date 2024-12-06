package main.java.com.example;

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

public class Interface extends Application {
    private Map<String, Utilisateur> utilisateurs;
    private Map<String, Stage> fenetresUtilisateurs;
    private TextArea logArea;
    private String caCertificat;
    private Map<String, Map<String, String>> certificatsVerifies;

    @Override
    public void start(Stage primaryStage) {
        utilisateurs = new HashMap<>();
        fenetresUtilisateurs = new HashMap<>();
        certificatsVerifies = new HashMap<>();
        primaryStage.setTitle("MiniRSA Communication");
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefRowCount(10);
        Button addUserButton = new Button("Ajouter un utilisateur");
        Button showUsersButton = new Button("Liste des utilisateurs");
        TextArea userListArea = new TextArea();
        userListArea.setEditable(false);
        userListArea.setPrefRowCount(5);

        addUserButton.setOnAction(e -> showAddUserDialog());
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

        initializeCA();
    }

    private void initializeCA() {
        try {
            CertificatAutorite ca = new CertificatAutorite(logArea);
            caCertificat = ca.getCaCertificat();
        } catch (Exception e) {
            if (logArea != null) {
                Platform.runLater(() -> logArea.appendText("Erreur lors de la génération du certificat CA : " + e.getMessage() + "\n"));
            }
            e.printStackTrace();
        }
    }

    private void showAddUserDialog() {
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
                createUserWindow(userName);
            } else {
                showAlert("Erreur", "Nom d'utilisateur invalide ou déjà utilisé");
            }
        });
    }

    private void createUserWindow(String username) {
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

        Utilisateur user = new Utilisateur(username, messageArea);
        utilisateurs.put(username, user);
        fenetresUtilisateurs.put(username, userStage);
        user.start();

        updateAllRecipientLists();

        Platform.runLater(() -> {
            logArea.appendText("=== Création de l'utilisateur " + username + " ===\n");
            logArea.appendText("Génération des clés RSA...\n");
            logArea.appendText("Clé publique: " + user.getKeyPair().getPublicKey() + "\n");
            logArea.appendText("Modulo: " + user.getKeyPair().getModulus() + "\n\n");
        });

        sendButton.setOnAction(e -> {
            String recipient = recipientBox.getValue();
            String message = messageField.getText();
            if (recipient != null && !message.isEmpty()) {
                Utilisateur destinataire = utilisateurs.get(recipient);
                if (destinataire != null) {
                    Platform.runLater(() -> {
                        logArea.appendText("=== Envoi de message de " + username + " à " + recipient + " ===\n");
                        logArea.appendText("1. Message original: " + message + "\n");
                        logArea.appendText("2. Calcul de l'empreinte...\n");
                        logArea.appendText("3. Signature du message...\n");
                        logArea.appendText("4. Chiffrement du message...\n");
                        logArea.appendText("5. Vérification du certificat du destinataire...\n");
                        logArea.appendText("6. Message envoyé\n\n");
                    });

                    if (!destinataire.isCertificatVerified()) {
                        try {
                            boolean certificatValide = Certificat.verifierCertificat(destinataire.getCertificat());
                            if (certificatValide) {
                                destinataire.setCertificatVerified(true);
                                certificatsVerifies.put(username, new HashMap<>());
                                certificatsVerifies.get(username).put(recipient, destinataire.getCertificat());
                                Platform.runLater(() -> logArea.appendText("Certificat du destinataire vérifié avec succès.\n"));
                            } else {
                                Platform.runLater(() -> logArea.appendText("Le certificat du destinataire n'est pas valide.\n"));
                                return;
                            }
                        } catch (Exception ex) {
                            Platform.runLater(() -> logArea.appendText("Erreur lors de la vérification du certificat du destinataire : " + ex.getMessage() + "\n"));
                            ex.printStackTrace();
                            return;
                        }
                    }

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
            Platform.runLater(() -> logArea.appendText("=== Déconnexion de " + username + " ===\n\n"));
        });
    }

    private void updateAllRecipientLists() {
        for (Map.Entry<String, Stage> entry : fenetresUtilisateurs.entrySet()) {
            Stage stage = entry.getValue();
            if (stage != null && stage.getScene() != null) {
                VBox layout = (VBox) stage.getScene().getRoot();
                if (layout != null && layout.getChildren().size() > 3) {
                    Node node = layout.getChildren().get(3);
                    if (node instanceof ComboBox) {
                        ComboBox<String> recipientBox = (ComboBox<String>) node;
                        String currentUser = entry.getKey();
                        updateRecipientList(recipientBox, currentUser);
                    }
                }
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateRecipientList(ComboBox<String> recipientBox, String currentUser) {
        if (recipientBox == null || utilisateurs == null) {
            return;
        }
        recipientBox.getItems().clear();
        for (String user : utilisateurs.keySet()) {
            if (user != null && !user.equals(currentUser)) {
                recipientBox.getItems().add(user);
            }
        }
    }
}