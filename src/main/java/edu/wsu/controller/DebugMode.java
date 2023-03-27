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
    private static ComboBox[] comboBoxes;
    private static TextField[] textFields;

    public static void debug(App a){
        a.changeScene(DebugModeFX.newScene(a));
    }
    public static void start(App a){
        Model m = new Model();
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
                        m.addPlayer(new Innocent(name));
                        break;
                    case MURDERER:
                        m.addPlayer(new Murderer(name));
                        break;
                    case DETECTIVE:
                        m.addPlayer(new Detective(name));
                        break;
                    case DOCTOR:
                        m.addPlayer(new Doctor(name));
                        break;
                    case ENGINEER:
                        m.addPlayer(new Engineer(name));
                        break;
                }
            }
        }
        if(m.countPlayers() > 0) {
            m.setAppLink(a);
            a.beginGameFromDebugging(m);
        }
    }
}
