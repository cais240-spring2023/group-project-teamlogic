package edu.wsu;

import edu.wsu.controller.MessageDisplayerFX;
import edu.wsu.model.Model;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Scene currentlyShowing;
    private Stage stage;

    @Override
    public void start(Stage stage){
        final int BUTTON_WIDTH = 125;

        Image nestor = new Image("file:./src/main/resources/nestor.png");
        ImageView nestorView = new ImageView(nestor);

        Button hotSeat = new Button("Hot Seat");
        hotSeat.setPrefWidth(BUTTON_WIDTH);
        hotSeat.setOnAction(event -> {test();});

        Button server = new Button("Launch server");
        server.setPrefWidth(BUTTON_WIDTH);

        Button client = new Button("Connect to server");
        client.setPrefWidth(BUTTON_WIDTH);

        Button roleList = new Button("View Role List");
        roleList.setPrefWidth(BUTTON_WIDTH);



        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        vbox.getChildren().add(nestorView);
        vbox.getChildren().add(hotSeat);
        vbox.getChildren().add(server);
        vbox.getChildren().add(client);
        vbox.getChildren().add(roleList);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("Nestor's Murder Mystery");
        stage.setScene(scene);
        currentlyShowing = scene;
        this.stage = stage;
        stage.show();
    }

    public void changeScene(Scene scene){
        if(currentlyShowing != null) stage.setScene(scene);
        currentlyShowing = scene;
    }

    public void test(){
        MessageDisplayerFX.display("Test","Testing123",this);
        MessageDisplayerFX.waiting();
        MessageDisplayerFX.display("Test","Testing456",this);
        MessageDisplayerFX.waiting();
    }

    public void runGame(){
        Model m = new Model();
        Model.setAppLink(this);
        m.gameLoop();
    }
}
