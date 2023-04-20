package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.*;
import edu.wsu.view.MessageDisplayerFX;
import edu.wsu.view.PlayerSelectorPics;
import edu.wsu.view.ProfileSelectorFX;
import edu.wsu.view.Problem;
import edu.wsu.view.Waiting;
import javafx.application.Platform;

import java.io.*;
import java.net.*;

public class Client {
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static App appLink;
    private static Model model;
    private static Player player;
    public static void launchClient(String hostName, int portNumber, App a) {
        appLink = a;
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
        model = ModelSingleton.getInstance();
        a.changeScene(ProfileSelectorFX.newScene(model,appLink));
    }
    public static void beginGame(){
        appLink.changeScene(Waiting.newScene());
        Thread thread = new Thread(() -> {
            String details = receive();//This will send name and role data
            System.out.println(details);
            int index = Integer.parseInt(details.split(";")[0]);
            String[] names = details.split(";")[1].split(" ");
            String[] roles = details.split(";")[2].split(" ");
            for(int i = 0; i < names.length; i++){
                switch(roles[i]){
                    case "Innocent":
                        model.addPlayer(new Innocent(names[i]));
                        break;
                    case "Murderer":
                        model.addPlayer(new Murderer(names[i]));
                        break;
                    case "Detective":
                        model.addPlayer(new Detective(names[i]));
                        break;
                    case "Doctor":
                        model.addPlayer(new Doctor(names[i]));
                        break;
                    case "Engineer":
                        model.addPlayer(new Engineer(names[i]));
                        break;
                    case "Janitor":
                        model.addPlayer(new Janitor(names[i]));
                        break;
                    case "Silencer":
                        model.addPlayer(new Silencer(names[i]));
                        break;
                    case "Trickster":
                        model.addPlayer(new Trickster(names[i]));
                        break;
                }
            }
            player = model.getPlayer(index);
            model.tellRoles();
            Platform.runLater(()->goodMorning());
        });
        thread.start();
    }
    public static void goodMorning(){
        MessageDisplayerFX.display(player.getName()+".","Day " + model.getTurn() + "\nGood morning!\nLiving Players: "+model.listLivingPlayers()+"\n\nYour messages:\n"+player.getMessages(),appLink,model);
    }
    public static void nightPhase(){
        for(int i = 0; i < 12; i++){
            if(model.getPlayer(i) != null){
                if(model.getPlayer(i).isAlive()) System.out.println((i+1) + "." + model.getPlayer(i).getName());
                else System.out.println("X. " + model.getPlayer(i).getName());
            }
        }
        if(player.isAlive()) appLink.changeScene(PlayerSelectorPics.newScene(model.getPlayers(),player,player.getNightActionName(),appLink));
        else{
            appLink.changeScene(Problem.newScene("died"));
            nightHandler();
        }
    }
    public static void voting(){
        if(model.checkWinner() != null) goodGame();
        if(player.isAlive() && !player.isSilenced()) appLink.changeScene(PlayerSelectorPics.newScene(model.getPlayers(),player,"vote against",appLink));
        else{
            String s;
            if(player.isSilenced()) s = "been silenced";
            else s = "died";
            appLink.changeScene(Problem.newScene(s));
            voteHandler();
        }
    }
    public static void voteHandler(){
        Thread thread = new Thread(() -> {
            String details = receive();
            if(details.equals("None")) Platform.runLater(() -> MessageDisplayerFX.display(" ","Nobody was voted off the train.",appLink,model));
            else{
                Player p = model.getPlayer(details);
                p.kill();
                Platform.runLater(()->MessageDisplayerFX.display("Player killed! ",p.getName() + " was thrown off the train. Good luck to them!\n\nThey were " + p.roleString(),appLink, model));
            }
        });
        thread.start();
    }
    public static void goodGame(){
        Player[] winners = model.getWinners();
        String winnerString = new String();
        for(int i = 0; i < winners.length; i++){
            if(winners[i] != null) winnerString += winners[i].getName() + " ";
        }
        MessageDisplayerFX.display("Good game!\nWinners",winnerString,appLink,model);
    }
    public static void nightHandler(){
        appLink.changeScene(Waiting.newScene());
        Thread thread = new Thread(() -> {
            String details = receive().replace('\t','\n');
            String[] deadPlayers = details.split(";")[0].split(",");
            for(int i = 0; i < deadPlayers.length; i++){
                System.out.println(deadPlayers[i]);
            }
            String message = details.split(";")[1];
            player.hear(message);
            for(int i = 0; i < deadPlayers.length; i++){
                if(model.getPlayer(deadPlayers[i]) != null) model.getPlayer(deadPlayers[i]).kill();
                System.out.println("Killing" + model.getPlayer(deadPlayers[i]));
            }
            model.incrementTurn();
            Platform.runLater(Client::goodMorning);
        });
        thread.start();
    }
    public static void sendMessage(String message){
        try{
            out.println(message);
        }
        catch (NullPointerException e) {
            System.out.println("Something's gone very wrong");
        }
    }
    public static String receive(){
        try {
            String received = in.readLine();
            return received;
        }
        catch (IOException e) {
            System.err.println("IO error");
            System.exit(1);
        }
        return null;
    }
}