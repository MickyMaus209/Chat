package de.mickymaus209.chatserver;

import de.mickymaus209.chatserver.backend.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class ChatServer {
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static Server server;

    public static void main(String[] args) throws IOException {
        LOGGER.info("Server has started." + "\n" + "Please bind the port");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        server = new Server(Integer.parseInt(reader.readLine()));
        new Thread(()->{
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        listenToCommands();
    }

    private static void listenToCommands(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread(()->{
            String input;
            while (true){
                try {
                    if ((input = reader.readLine()) == null) break;
                    if(input.equalsIgnoreCase("!stop")){
                        server.stop();
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
