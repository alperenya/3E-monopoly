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

    public void initializeMenu(){} //Runs at the start of the game
    public void openCredits(){} //Runs on clicking credits button
    public void openSettings(){} //Runs on clicking settings button
    public void exit(){} //Runs on clicking exit game
    public void openGame(){} //Runs on clicking game creation button
    public void startGame(ArrayList<Player> players, ArrayList<Cell> cells){} //Runs after deciding player count and starting money. Cells will not be changing can be removed.
    public void movePlayer(Player player, Cell toPosition){} //Moves the player from a position to a position. Will run after rolling dice.
    public void openTradeScreen(Player currentPlayer, Player withPlayer){} //Opens trade screen for current player with the player he chooses to trade
    public void showMessage(){} //Show an informing message
    public void rollDice(){} //Activate the dice rolling animation
    public void updateGame(){} //Updates the state of the board. Might run after a turn ends

    public void buyProperty(Player currentPlayer, Property property){

    } //After current player buys a property

    public void sellProperty(Player currentPlayer, Property property){} //After current player sells a property
    public void pauseGame(){} //Pause the game in the current state
    public void celebrateWinner(Player winner){ System.out.println("winner.getName() + has won the game"); } //Activate the celebration animation for a given player
    public void buildHouse(Player currentPlayer, Property property){} //After current player builds a house
    public void buildHospital(Player currentPlayer, Property property){} //After current player builds a hospital
    public void mortgageProperty(Player currentPlayer, Property property){} //After current player mortgages a property

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
        GameEngine engine = new GameEngine();
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of players: ");
        int playerCount = sc.nextInt();
        engine.startGame(playerCount);
        engine.gameFlow();
        //launch(args);
    }
}
