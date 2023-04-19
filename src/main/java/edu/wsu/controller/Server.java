package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import edu.wsu.view.PlayersList;
import javafx.application.Platform;

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
            if(communicators[i] != null) m.addPlayer(new Player(communicators[i].getPlayerName()));
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
        System.out.println("Night phase");
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
                        int finalI = i;
                        Platform.runLater(() -> PlayersList.addName(messages[finalI]));
                    }
                }
            }
            nightHandler();
        });
        thread.start();
    }
    public static void nightHandler(){
        Model model = ModelSingleton.getInstance();
        for(int i = 0; i < messages.length; i++){//find all the mail the players sent to each other
            if(messages[i] != null && messages[i].split(" -> ").length == 3){
                model.getPlayer(messages[i].split(" -> ")[0]).visited(model.getPlayer(messages[i].split(" -> ")[2]));
                model.getPlayer(messages[i].split(" -> ")[2]).hear(messages[i].split(" -> ")[1]);
                System.out.println("3 " + messages[i]);
            }
        }
        for(int i = 0; i < messages.length; i++) {
            if(messages[i] != null && messages[i].split(" -> ").length == 2){
                model.getPlayer(messages[i].split(" -> ")[0]).visited(model.getPlayer(messages[i].split(" -> ")[1]));
            }
        }
        for(int i = 0; i < messages.length; i++) {//Night action handling
            if(messages[i] != null && messages[i].split(" -> ").length == 2){
                model.getPlayer(messages[i].split(" -> ")[0]).nightHandler(model.getPlayer(messages[i].split(" -> ")[1]));
                System.out.println("2 " + messages[i]);
            }
        }
        String deadPlayers = "";
        for(int i = 0; i < communicators.length; i++){
            if(model.getPlayer(i) != null && !model.getPlayer(i).isAlive()) deadPlayers += model.getPlayer(i).getName() + ",";
        }
        if(!deadPlayers.isEmpty()) deadPlayers = deadPlayers.substring(0,deadPlayers.length()-1);
        for(int i = 0; i < communicators.length; i++){
            if(communicators[i] != null){
                communicators[i].send(deadPlayers + ";" + model.getPlayer(i).getMessages().replace('\n','\t'));
            }
        }
        model.incrementTurn();
        if(model.checkWinner() != null) voting();
    }
    public static void voting(){
        System.out.println("Voting");
        clearMessages();
        appLink.changeScene(PlayersList.newScene("Votes:"));
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
                        int finalI = i;
                        Platform.runLater(() -> PlayersList.addName(messages[finalI]));
                    }
                }
            }
            voteCounting();
        });
        thread.start();
    }
    public static void voteCounting(){
        Model model = ModelSingleton.getInstance();
        int[] votes = new int[communicators.length];
        for(int i = 0; i < communicators.length; i++){
            votes[i] = 0;
        }
        int totalVotes = 0;
        for(int i = 0; i < messages.length; i++){
            if(messages[i] != null){
                totalVotes++;
                votes[model.getPlayerIndex(model.getPlayer(messages[i].split(" -> ")[1]))]++;
            }
        }
        for(int i = 0; i < votes.length; i++){
            if(votes[i] < totalVotes / 2){
                for(int j = 0; j < communicators.length; j++){
                    communicators[j].send(model.getPlayer(i).getName());
                }
                totalVotes = Integer.MAX_VALUE;//a little control thing so I don't have to make a boolean
                break;
            }
        }
        if(totalVotes != Integer.MAX_VALUE){
            for(int j = 0; j < communicators.length; j++){
                communicators[j].send("None");
            }
        }
        if(model.checkWinner() != null) nightPhase();
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
