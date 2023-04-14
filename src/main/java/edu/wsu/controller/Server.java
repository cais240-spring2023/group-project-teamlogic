package edu.wsu.controller;

import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import edu.wsu.view.PlayersList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Communicator[] communicators = new Communicator[12];
    static boolean filling = true;

    public static void runServer(){
        int portNumber = 4544;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            int i = 0;
            while(filling){
                communicators[i] = new Communicator(serverSocket.accept());
                i++;
            }
            for(i = 0; i < communicators.length; i++){
                if(communicators[i] != null) communicators[i].start();
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    }
    public static void launch(){
        filling = false;
    }
    public Player getPlayer(Communicator communicator){
        Model model = ModelSingleton.getInstance();
        int index = -1;
        for(int i = 0; i < communicators.length; i++){
            if(communicator == communicators[i]) index = i;
        }
        return model.getPlayer(index);
    }
    public Communicator getCommunicator(Player player){
        Model model = ModelSingleton.getInstance();
        int index = model.getPlayerIndex(player);
        try{
            return communicators[index];
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

}
