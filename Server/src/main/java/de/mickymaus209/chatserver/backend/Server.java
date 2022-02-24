package de.mickymaus209.chatserver.backend;

import de.mickymaus209.chatserver.ChatServer;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private int port;
    public final static List<Client> CLIENTS = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        ChatServer.LOGGER.info("Server port bound");
        waitForClients();
    }

    private void waitForClients() throws IOException {
        while (true) {
            Client client = new Client(serverSocket.accept());
            CLIENTS.add(client);
            ChatServer.LOGGER.info("A new Client (" + client.getSocket().getInetAddress() + ":" + client.getSocket().getPort() + ") has connected to the server.");
            startThreadToReadDataFromClient(client);
        }
    }

    private void startThreadToReadDataFromClient(Client client) {
       new Thread(() -> {
            String message = "";
            while (true) {
                try {
                    if ((message = client.getReader().readLine()) == null) break;
                    sendDataToEachClient(message);
                } catch (IOException e) {
                    client.disconnect();
                    break;
                }
            }
        }).start();
    }

    public void sendDataToEachClient(String data) {
        CLIENTS.forEach(client -> client.getWriter().println(data));
        ChatServer.LOGGER.info(data);
    }

    public void stop() throws IOException {
        CLIENTS.forEach(Client::disconnect);
        serverSocket.close();
        serverSocket = null;
    }
}
