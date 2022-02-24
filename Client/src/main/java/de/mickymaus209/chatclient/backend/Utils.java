package de.mickymaus209.chatclient.backend;

public class Utils {

    public boolean isNumeric(String text){
        try{
            Integer.parseInt(text);
        }catch (NumberFormatException exception){
            return false;
        }
        return true;
    }
}
