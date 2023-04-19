package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.view.ProfileSelectorFX;

import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static App appLink;
    private static Model model;
    private static String player;
    public static void launchClient(String hostName, int portNumber, App a) {
        appLink = a;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        model = ModelSingleton.getInstance();
        a.changeScene(ProfileSelectorFX.newScene(model,appLink));
    }
    public static void beginGame(){
        String details = receive();//This will send name and role data
        String[] names = details.split(",");
    }
    public static void sendMessage(String message){
        try{
            out.println(message);
        }
        catch (NullPointerException e) {
            System.out.println("Something's gone very wrong");
        }
    }
    public static String receive(){
        try {
            String received = in.readLine();
            return received;
        }
        catch (IOException e) {
            System.err.println("IO error");
            System.exit(1);
        }
        return null;
    }
}