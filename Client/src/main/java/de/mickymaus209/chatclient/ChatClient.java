package de.mickymaus209.chatclient;

import de.mickymaus209.chatclient.frontend.Gui;

import java.util.logging.Logger;

public class ChatClient {
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        new Gui().launchApp(args);
    }
}
