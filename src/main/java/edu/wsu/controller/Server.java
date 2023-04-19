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
    private static String[] messages = new String[12];
    private static int received;
    private static boolean[] temp;
    private static int playerCount;
    private static boolean filling = true;
    private static App appLink;

    public static void runServer(App a){
        appLink = a;
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
    public static void launch(){
        Model m = ModelSingleton.getInstance();
        filling = false;
        for(int i = 0; i < communicators.length; i++){
            if(communicators[i] != null) m.addPlayer(new Player(communicators[i].getName()));
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
        playerCount = 0;
        for (int i = 0; i < 12; i++) {
            if (communicators[i] != null){
                communicators[i].send(i + ";" + details);
                playerCount++;
            }
        }
        nightPhase();
    }

    public static void nightPhase(){
        clearMessages();
        appLink.changeScene(PlayersList.newScene("Night inputs:"));
        for(int i = 0; i < communicators.length; i++){
            if(communicators[i] != null) communicators[i].receive();
        }
        temp = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false};
        Thread thread = new Thread(() -> {
            while(received < playerCount){
                System.out.print("");
                for(int i = 0; i < messages.length; i++){
                    if(messages[i] != null && !temp[i]){
                        temp[i] = true;
                        PlayersList.addName(messages[i]);
                    }
                }
            }
            nightHandler();
        });
        thread.run();
    }
    public static void nightHandler(){
        Model model = ModelSingleton.getInstance();
        String[] outMessages = new String[communicators.length];
        for(int i = 0; i < messages.length; i++){
            outMessages[i] = new String();
        }
        for(int i = 0; i < messages.length; i++){//find all the mail the players sent to each other
            if(messages[i] != null && messages[i].split(" -> ").length == 3){
                model.getPlayer(messages[i].split(" -> ")[2]).hear(messages[i].split(" -> ")[1]);
            }
        }
        for(int i = 0; i < messages.length; i++) {//Night action handling
            if(messages[i] != null && messages[i].split(" -> ").length == 2){
                model.getPlayer(messages[i].split(" -> ")[1]).nightHandler(model.getPlayer(messages[i].split(" -> ")[0]));
            }
        }
        String deadPlayers = "";
        for(int i = 0; i < communicators.length; i++){
            if(!model.getPlayer(i).isAlive()) deadPlayers += model.getPlayer(i).getName() + ",";
        }
        if(!deadPlayers.isEmpty()) deadPlayers = deadPlayers.substring(0,deadPlayers.length()-1);
        for(int i = 0; i < communicators.length; i++){
            if(communicators[i] != null){
                communicators[i].send(deadPlayers + ";" + model.getPlayer(i).getMessages().replace('\n','\t'));
            }
        }

    }
    public static void receive(String message, Communicator communicator){
        int i = 0;
        while(communicators[i] != communicator){
            i++;
        }
        System.out.println(message);
        messages[i] = message;
        received++;
    }
    public static void clearMessages(){
        received = 0;
        messages = new String[12];
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
