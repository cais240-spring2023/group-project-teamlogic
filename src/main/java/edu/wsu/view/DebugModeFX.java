package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.controller.DebugMode;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
public class DebugModeFX {


    private static Label[] labels;
    private static ComboBox[] comboBoxes;
    private static TextField[] textFields;
    public static Scene newScene(App a){
        Label text = new Label("So it's come to this...");
        GridPane g = new GridPane();
        Label p1t = new Label("Player 1");
        TextField p1f = new TextField("");
        ComboBox<String> p1c = new ComboBox<>();
        p1c.getItems().addAll(Model.Role.getStrings());

        Label p2t = new Label("Player 2");
        TextField p2f = new TextField("");
        ComboBox<String> p2c = new ComboBox<>();
        p2c.getItems().addAll(Model.Role.getStrings());

        Label p3t = new Label("Player 3");
        TextField p3f = new TextField("");
        ComboBox<String> p3c = new ComboBox<>();
        p3c.getItems().addAll(Model.Role.getStrings());

        Label p4t = new Label("Player 4");
        TextField p4f = new TextField("");
        ComboBox<String> p4c = new ComboBox<>();
        p4c.getItems().addAll(Model.Role.getStrings());

        Label p5t = new Label("Player 5");
        TextField p5f = new TextField("");
        ComboBox<String> p5c = new ComboBox<>();
        p5c.getItems().addAll(Model.Role.getStrings());

        Label p6t = new Label("Player 6");
        TextField p6f = new TextField("");
        ComboBox<String> p6c = new ComboBox<>();
        p6c.getItems().addAll(Model.Role.getStrings());

        Label p7t = new Label("Player 7");
        TextField p7f = new TextField("");
        ComboBox<String> p7c = new ComboBox<>();
        p7c.getItems().addAll(Model.Role.getStrings());

        Label p8t = new Label("Player 8");
        TextField p8f = new TextField("");
        ComboBox<String> p8c = new ComboBox<>();
        p8c.getItems().addAll(Model.Role.getStrings());

        Label p9t = new Label("Player 9");
        TextField p9f = new TextField("");
        ComboBox<String> p9c = new ComboBox<>();
        p9c.getItems().addAll(Model.Role.getStrings());

        Label p10t = new Label("Player 10");
        TextField p10f = new TextField("");
        ComboBox<String> p10c = new ComboBox<>();
        p10c.getItems().addAll(Model.Role.getStrings());

        Label p11t = new Label("Player 11");
        TextField p11f = new TextField("");
        ComboBox<String> p11c = new ComboBox<>();
        p11c.getItems().addAll(Model.Role.getStrings());

        Label p12t = new Label("Player 12");
        TextField p12f = new TextField("");
        ComboBox<String> p12c = new ComboBox<>();
        p12c.getItems().addAll(Model.Role.getStrings());

        labels = new Label[] {p1t,p2t,p3t,p4t,p5t,p6t,p7t,p8t,p9t,p10t,p11t,p12t};
        textFields = new TextField[] {p1f,p2f,p3f,p4f,p5f,p6f,p7f,p8f,p9f,p10f,p11f,p12f};
        comboBoxes = new ComboBox[] {p1c,p2c,p3c,p4c,p5c,p6c,p7c,p8c,p9c,p10c,p11c,p12c};

        Button submit = new Button("Submit");
        submit.setOnAction(event -> {DebugMode.start(a,textFields,comboBoxes);});

        int i;
        for(int c = 0; c < 2; c++){
            for(int r = 0; r < 6; r++){
                i = r+6*c;
                g.add(labels[i],c*3,r);
                g.add(textFields[i],c*3+1,r);
                g.add(comboBoxes[i],c*3+2,r);
            }
        }
        VBox v = new VBox();
        v.getChildren().add(text);
        v.getChildren().add(g);
        v.getChildren().add(submit);
        v.setAlignment(Pos.CENTER);

        return new Scene(v,600,300);
    }
}
