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
    private String name;
    public Communicator(Socket socket){
        this.socket = socket;
    }
    public String getPlayerName(){
        return name;
    }
    public void run(){
        try {
            System.out.println("Thread begun");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receive();
            while(received.equals("")){
                System.out.print("");//This has to be there or it breaks the program
            }
            System.out.println("---\n" + received);
            Platform.runLater(() -> PlayersList.addName(received));
        }
        catch(IOException e){

        }
    }
    public void send(String message){
        out.println(message);
    }
    public void receive(){
        System.out.println("Receiving...");
        Thread thread = new Thread(() -> {

            try {
                received = in.readLine();
                Server.receive(received,this);
            }
            catch (IOException e) {
                System.err.println("IO error");
                System.exit(1);
            }
        });
        thread.start();
    }
}
