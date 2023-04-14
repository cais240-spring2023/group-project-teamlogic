package edu.wsu.controller;

import edu.wsu.App;

import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static App appLink;
    public static void launchClient(String hostName, int portNumber, App a) {
        appLink = a;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            appLink.beginGame();
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
}