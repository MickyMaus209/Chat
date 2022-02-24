package de.mickymaus209.chatserver.backend;

import de.mickymaus209.chatserver.ChatServer;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintStream writer;
    private InputStream in;
    private OutputStream out;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        init();
    }

    public void init() throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new PrintStream(out, true);
    }

    public void disconnect() {
        try {
            ChatServer.LOGGER.info(socket.getInetAddress() + ":" + socket.getPort() + " disconnected.");
            Server.CLIENTS.remove(this);
            writer.close();
            reader.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintStream getWriter() {
        return writer;
    }

    public Socket getSocket() {
        return socket;
    }
}
