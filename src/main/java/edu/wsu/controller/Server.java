package edu.wsu.controller;

import edu.wsu.App;
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
                Thread thread = new Thread(() -> {
                    try {
                        int i = 0;
                        while (filling) {
                            communicators[i] = new Communicator(serverSocket.accept());
                            communicators[i].start();
                            i++;
                            if (i >= 12) filling = false;
                        }
                    }
                    catch (IOException e) {
                        System.err.println("Couldn't get I/O for the connection");
                        System.exit(1);
                    }
                });
                thread.start();
            }
            catch(IOException e){
                System.err.println("Couldn't get I/O for the connection");
                System.exit(1);
            }
    }
    public static void launch(String[] names){
        Model m = ModelSingleton.getInstance();
        filling = false;
        for(int i = 0; i < names.length; i++){
            if(names[i] != null) m.addPlayer(new Player(names[i]));
        }
        String details = new String();
        m.assignRoles();
        System.out.println(m.getPlayer(0).getName());
        for(int i = 0; i < 12; i++){
            if(m.getPlayer(i) != null) details += m.getPlayer(i).getName() + " ";
        }
        details = details.substring(0,details.length()-1);//cut off the final space
        details += ";";
        for(int i = 0; i < 12; i++){
            if(m.getPlayer(i) != null) details += m.getPlayer(i).roleName() + " ";
        }
        details = details.substring(0,details.length()-1);//cut off the final space
        for (int i = 0; i < 12; i++) {
            if (communicators[i] != null) communicators[i].send(i + ";" + details);
        }
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
