package de.mickymaus209.chatclient.backend;

import de.mickymaus209.chatclient.ChatClient;
import de.mickymaus209.chatclient.frontend.Gui;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private final Gui gui;
    private final Socket socket;

    private Thread readThread;
    private OutputStream out;
    private InputStream in;
    private BufferedReader reader;
    private PrintStream writer;

    public Client(String host, int port, Gui gui) {
        this.host = host;
        this.port = port;
        this.gui = gui;
        socket = new Socket();
    }

    /**
     * connect to a server
     * initialize InputStream, OutputStream, BufferedReader and PrintStream to send and read data from/to the server.
     */
    public void connect() {
        try {
            socket.connect(new InetSocketAddress(host, port), 2000);
            ChatClient.LOGGER.info("Client connected to " + host + ":" + port);
            gui.getChat().setText("connected");
            out = socket.getOutputStream();
            in = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintStream(out, true);
            startThreadToReadDataFromServer();
            readThread.start();
        } catch (IOException e) {
            ChatClient.LOGGER.warning("server is not available");
        }
    }

    /**
     * start a new thread to read the data (Input) at the same time.
     * Reading the data happens on a different thread
     */
    private void startThreadToReadDataFromServer() {
        readThread = new Thread(() -> {
            String message;
            while (isConnected()) {
                try {
                    if ((message = reader.readLine()) == null) break;
                    gui.getChat().setText(gui.getChat().getText() + "\n" + message);
                } catch (IOException ignored) {}
            }
        });
    }

    /**
     * send data to the connected server
     * using PrintWriter (OutputStream)
     */
    public void sendDataToServer(String data) {
        writer.println(data);
    }

    /**
     * it closes the connection to the server (socket), the writer (OutputStream) and the reader (InputStream)
     * it will disconnect to the current server connection.
     */
    public void disconnect() {
        try {
            socket.close();
            if (in != null && out != null) {
                reader.close();
                writer.close();
                in.close();
                out.close();
                readThread.interrupt();
                ChatClient.LOGGER.info("The connection to the server has been closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * check if the client is connected to a server.
     *
     * @return boolean if the client has a connection to a server
     */
    public boolean isConnected() {
        return !socket.isClosed() && socket.isConnected() && in != null && out != null;
    }
}
