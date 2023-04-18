package edu.wsu.controller;

import edu.wsu.view.PlayersList;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator extends Thread{
    private Socket socket;
    private boolean waiting;
    private String toSend = new String();
    private String received;
    private boolean sending = false;
    private boolean receiving = false;
    private PrintWriter out;
    private BufferedReader in;
    private SendTo sendTo;
    public Communicator(Socket socket){
        this.socket = socket;
        this.waiting = true;
    }
    public void run(){
        System.out.println("Thread begun");
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiving = true;
            sendTo = SendTo.USERNAMES;
            while(waiting){
                if(sending && !toSend.isEmpty()){
                    out.println(toSend);
                    toSend = "";
                    sending = true;
                }
                else if(receiving){
                    received = in.readLine();
                    receiving = false;
                    switch(sendTo){
                        case USERNAMES:
                            Platform.runLater(() -> PlayersList.addName(received));
                            break;
                    }
                }
            }
        }
        catch(IOException e){

        }
    }
    public void send(String message){
        this.sending = true;
        this.toSend = message;
    }
    public void receive(SendTo sendTo){
        this.receiving = true;
    }
    public static enum SendTo{USERNAMES}
}
