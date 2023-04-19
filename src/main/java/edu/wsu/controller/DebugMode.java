package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.*;
import edu.wsu.model.Innocent;
import edu.wsu.model.Murderer;
import edu.wsu.model.Detective;
import edu.wsu.model.Model;
import edu.wsu.view.DebugModeFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
public class DebugMode {


    private static Label[] labels;

    public static void debug(App a){
        a.changeScene(DebugModeFX.newScene(a));
    }
    public static void start(App a, TextField[] textFields, ComboBox[] comboBoxes){
        Model model = ModelSingleton.getInstance();
        String name;
        Model.Role role;
        int index;
        int count;
        for(int i = 0; i < 12; i++){
            name = textFields[i].getText();
            index = comboBoxes[i].getSelectionModel().getSelectedIndex();
            if(name.equals("")) break;
            else if(index == -1) break;
            else{
                role = Model.Role.get(index);
                switch(role){
                    case INNOCENT:
                        model.addPlayer(new Innocent(name));
                        break;
                    case MURDERER:
                        model.addPlayer(new Murderer(name));
                        break;
                    case DETECTIVE:
                        model.addPlayer(new Detective(name));
                        break;
                    case DOCTOR:
                        model.addPlayer(new Doctor(name));
                        break;
                    case ENGINEER:
                        model.addPlayer(new Engineer(name));
                        break;
                    case JANITOR:
                        model.addPlayer(new Janitor(name));
                        break;
                    case SILENCER:
                        model.addPlayer(new Silencer(name));
                        break;
                    case TRICKSTER:
                        model.addPlayer(new Trickster(name));
                        break;
                }
            }
        }
        if(model.countPlayers() > 0) {
            model.setAppLink(a);
            a.beginGameFromDebugging();
        }
    }
}
