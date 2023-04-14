package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.view.ProfileSelectorFX;
import edu.wsu.view.UsernameInputFX;


//ignore this for sprint 1
public class UsernameInput{
    //The model should be called and the name should be passed to the playerCreate


    //Creates an array of names with the size of 12
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
            complete(a);
        }

    }

    public static void complete(App a){
        Model m = a.getModel();
        m.addPlayersPhase(playerName);
        a.beginGame();
    }
    public static void complete(App a, String[] playerNames){
        Model m = a.getModel();
        m.addPlayersPhase(playerNames);
        a.beginGame();
    }

    public static void namer(Model m, App a) {
        if(a.DEBUG_MODE) a.changeScene(UsernameInputFX.newScene(m,a));
        else a.changeScene(ProfileSelectorFX.newScene(m,a));
    }
}
