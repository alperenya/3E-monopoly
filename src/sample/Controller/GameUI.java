package sample.Controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class GameUI extends Application {

    //Properties
    ArrayList<Label> properties;

    public GameUI(){
        properties = new ArrayList<Label>();
    } //Default constructorgit

    //properties
    private double xOffset = 0;
    private double yOffset = 0;

    private Stack scenes;

    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/sample.fxml"));

        primaryStage.initStyle( StageStyle.UNDECORATED );

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        //String musicFile = "src/sample/songs/drum.mp3";     // For example

        //Media sound = new Media(new File(musicFile).toURI().toString());
        //mediaPlayer = new MediaPlayer(sound);


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
       /* GameEngine engine = new GameEngine();
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of players: ");
        int playerCount = sc.nextInt();
        engine.startGame(playerCount);
        engine.gameFlow();*/
        launch(args);
    }
}
