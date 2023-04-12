package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.view.UsernameInputFX;

public class ProfileSelector {
    public static String[] playerName = new String[12];

    //Index of the array used to assign names to slots
    public static int currentIndex = 0;
    //Label to instruct the user to input the name

    //Move stage over to App
    //return the scene to app
    //end of startGame
    //At the end change the button from closing to beginGame
    public static void nameGetter(String input, Model m, App a){
        playerName[currentIndex] = input;
        currentIndex++; // increment the index
        System.out.println("Name added: " + input); //Just making sure the name got processed
        if(currentIndex >= 12){
            System.out.println("No more room");
            complete(m,a);
        }

    }

    public static void complete(Model m, App a){
        m.addPlayersPhase(playerName);
        a.beginGame();
    }

    public static void namer(Model m, App a) {
        a.changeScene(UsernameInputFX.newScene(m,a));
    }
}
