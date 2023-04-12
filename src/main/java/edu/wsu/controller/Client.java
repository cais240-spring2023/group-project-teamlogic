package edu.wsu.controller;

import java.io.*;
import java.net.*;

public class Client {
    private static String hostName = "localhost";
    private static int portNumber = 4544;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    public static void launchClient() {
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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