package edu.wsu.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator extends Thread{
    private Socket socket;
    private boolean waiting;
    public Communicator(Socket socket){
        this.socket = socket;
        this.waiting = true;
    }
    public void run(){
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e){

        }
        while(waiting){

        }
    }
}
