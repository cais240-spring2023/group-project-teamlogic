package edu.wsu.controller;

import edu.wsu.view.PlayersList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator extends Thread{
    private Socket socket;
    private boolean waiting;
    private String toSend = new String();
    private PrintWriter out;
    private BufferedReader in;
    public Communicator(Socket socket){
        this.socket = socket;
        this.waiting = true;
    }
    public void run(){
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(waiting){
                if(!toSend.isEmpty()){
                    out.println(toSend);
                    toSend = new String();
                }
                else{
                    PlayersList.addName(in.readLine());
                }
            }
        }
        catch(IOException e){

        }
    }
    public void send(String message){
        this.toSend = message;
    }
}
