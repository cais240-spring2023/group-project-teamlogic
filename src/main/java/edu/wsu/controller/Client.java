package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.*;
import edu.wsu.view.MessageDisplayerFX;
import edu.wsu.view.ProfileSelectorFX;
import edu.wsu.view.Waiting;

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
        String details = receive();//This will send name and role data
        int index = Integer.parseInt(details.split("\n")[0]);
        String[] names = details.split("\n")[1].split(" ");
        String[] roles = details.split("\n")[2].split(" ");
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
        goodMorning();
    }
    public static void goodMorning(){
        MessageDisplayerFX.display(player.getName(),"Good morning!\nLiving Players: "+model.listLivingPlayers()+"\n\nYour messages:\n"+player.getMessages(),appLink,model);
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