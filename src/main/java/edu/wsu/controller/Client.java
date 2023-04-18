package edu.wsu.controller;

import edu.wsu.App;

import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static App appLink;
    private static boolean running = true;
    private static boolean sending = false;
    private static boolean receiving = false;
    private static String received;
    private static String player;
    public static void launchClient(String hostName, int portNumber, App a) {
        appLink = a;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            appLink.startGame();
            Thread thread = new Thread(() -> {
                while(running){
                    if(receiving){
                        try {
                            received = in.readLine();
                            receiving = false;
                            parse();
                        } catch (IOException e) {
                            System.err.println("Couldn't get I/O for the connection to " + hostName);
                            System.exit(1);
                        }
                    }
                }
            });
            thread.start();
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
    public static void sendMessage(String message){
        try{
            out.println(message);
        }
        catch (NullPointerException e) {
            System.out.println("Something's gone very wrong");
        }
    }
    public static void receive(){
        receiving = true;
    }
    public static void parse(){
        if(received != null){
            String[] names = received.split(" ");
            received = null;
            UsernameInput.complete(appLink,names);
        }
    }
}