package de.mickymaus209.chatclient.backend;

import de.mickymaus209.chatclient.frontend.Gui;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Client client;
    private Gui gui;

    private final Utils utils = new Utils();

    public Controller(Gui gui){
        this.gui = gui;
    }

    @FXML
    public void onConnectButtonClicked(){
        String username = gui.getUserNameField().getText();
        String host = gui.getHostField().getText();
        String port = gui.getPortField().getText();
        if(host.isBlank() || port.isBlank() || !utils.isNumeric(port) || username.isBlank()){
            return;
        }
        client = new Client(host, Integer.parseInt(port), gui);
        client.connect();
    }

    @FXML
    public void onDisconnectButtonClicked(){
        if(client != null){
            client.disconnect();
            gui.getChat().setText("not connected");
        }
    }

    @FXML
    public void onEnterButtonClicked(){
        String username = gui.getUserNameField().getText();
        String message = gui.getMessageField().getText();
        if(client == null || !client.isConnected() || message.isBlank() || username.isBlank()){
            return;
        }
        client.sendDataToServer(username + ":" + message);
        gui.getMessageField().setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
