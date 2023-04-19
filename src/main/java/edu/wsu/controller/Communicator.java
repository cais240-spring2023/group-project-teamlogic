package edu.wsu.controller;

import edu.wsu.view.PlayersList;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator extends Thread{
    private final Socket socket;
    private boolean waiting;
    private String received = "";
    private PrintWriter out;
    private BufferedReader in;
    public Communicator(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receive();
            while(received.isEmpty()){}
            PlayersList.addName(received);
            received = "";
        }
        catch(IOException e){

        }
    }
    public void send(String message){
        out.println(message);
    }
    public void receive(){
        Thread thread = new Thread(() -> {

            try {
                received = in.readLine();
            }
            catch (IOException e) {
                System.err.println("IO error");
                System.exit(1);
            }
        });
        thread.start();
    }
}
