package de.mickymaus209.chatclient.frontend;

import de.mickymaus209.chatclient.ChatClient;
import de.mickymaus209.chatclient.backend.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static javafx.application.Platform.exit;

public class Gui extends Application {
    private TextField hostField, portField, userNameField, messageField;
    private TextArea chat;

    @Override
    public void start(Stage stage) throws Exception {
        ChatClient.LOGGER.info("User is using java " + System.getProperty("java.version"));
        ChatClient.LOGGER.info("User is using javafx " + System.getProperty("javafx.version"));
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(new Controller(this));
        stage.setScene(new Scene(fxmlLoader.load(getClass().getResourceAsStream("/fxml/mainView.fxml"))));
        stage.setTitle("ChatClient");
        stage.show();
        stage.setOnCloseRequest(event -> {
            try {
                exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        initElements(stage);
    }

    private void initElements(Stage stage){
        hostField = (TextField) stage.getScene().lookup("#hostField");
        portField = (TextField) stage.getScene().lookup("#portField");
        userNameField = (TextField) stage.getScene().lookup("#userNameField");
        messageField = (TextField) stage.getScene().lookup("#messageField");
        chat = (TextArea) stage.getScene().lookup("#chat");
    }

    public void launchApp(String[] args) {
        launch(args);
    }

    public TextField getHostField() {
        return hostField;
    }

    public TextField getPortField() {
        return portField;
    }

    public TextField getMessageField() {
        return messageField;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextArea getChat() {
        return chat;
    }
}
