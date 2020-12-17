package sample.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sample.Model.*;
//import sun.security.jca.GetInstance;

import javax.smartcardio.Card;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    //Properties
    @FXML
    private Button closeButton;
    private MediaPlayer mediaPlayer;
    @FXML private Slider soundControl;
    @FXML private Pane player_piece;

    private final int MAX_PLAYERS = 6; //Will be decided after pressing create game button
    private final int STARTING_MONEY = 10000;
    private int playerCount;
    private int botCount; //Newly added. Will be decided after pressing create game button
    private int turns; //Will be set to zero at the start of the game
    private ArrayList<String> pieces;
    private double gameVolume; //Current game volume. Will be 50% initially.

    private GameUI gameUI; //Reference to gameui component
    private GameMap gameMap; //Reference to the gamemap component
    private ArrayList<Player> players; //Reference to all players
    private Player currentPlayer; //Newly added. Current player of the turn
    private Dice dice; //Reference to the dice

    public GameEngine(){
        playerCount = 0;
        botCount = 0;
        turns = 0;
        pieces = new ArrayList<>(Arrays.asList("car", "hamburger", "glass", "computer", "football", "hat"));
        gameVolume = 0.5;
        gameUI = new GameUI();
        gameMap = GameMap.getInstance();
        players = new ArrayList<>();
        currentPlayer = null;
        dice = new Dice();
    } //Default constructor

    public int getMaxPlayers(){ return MAX_PLAYERS;}
    public int getTurns(){ return turns;}
    public int getStartingMoney(){ return STARTING_MONEY;}

    public GameUI getGameUI(){ return gameUI;}
    public GameMap getMap(){ return gameMap;}
    public ArrayList<Player> getPlayers(){ return players;}
    public Dice getDice(){ return dice;}
    public Player getCurrentPlayer(){return currentPlayer;}
    public void setBotCount(int botCount){ this.botCount = botCount;}

    //These methods will be working inside of the engine so I think they can be changed to private.
    public void startGame(int playerCount){ //Runs after deciding maxPlayers and
        this.playerCount = playerCount;
        System.out.println("There are " + playerCount + " players. " + (MAX_PLAYERS-playerCount) + " bot(s) can be added.");
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the number of bot: ");
        setBotCount(sc.nextInt());

        createMap();
        createPlayers();
        currentPlayer = players.get(0);
    }
    public void updateUI(){} //Update game ui between turns
    public boolean finishGame(){
        int bankrupted = 0;
        for (Player p: players) {
            if(p.isBankrupt())
                bankrupted++;
        }
        return bankrupted >= players.size() - 1;
    } //Finish the game if everybody is bankrupt except one
    public void setGameVolume(double volume){
        gameVolume = volume;
    }
    public void muteGame(){
        gameVolume = 0;
    } //Make the volume 0

    public void manageProperties(){

        Cell currentPosition = currentPlayer.getPosition();

        if( currentPosition instanceof Property ){

            int price = ((Property) currentPlayer.getPosition()).getPrice();

            if( price <= currentPlayer.getMoney()){
                currentPlayer.buyProperty( (Property) currentPosition );
                ((Property) currentPosition).setOwner( currentPlayer );
                gameUI.buyProperty( currentPlayer, (Property) currentPosition );
            }
        }

    }

    public void gameFlow(){
        for (Cell c:gameMap.getCells()) {
            System.out.println("[ " +  (gameMap.getCells().indexOf(c)+1) + " " + c.getName() + ": " + c.getVisitorsPiece() + " ] ");
        }

        System.out.println();
        Scanner sc = new Scanner(System.in);

        while(!finishGame()){

            System.out.println("Current player: " + currentPlayer.getName() + ", " + currentPlayer.getPiece());
            System.out.println("Type R to roll dice");
            String input = sc.nextLine();

            if(input.equals("R")){
                movePlayer(dice.roll());
            }

            handleInfection();

            for (Cell c:gameMap.getCells()) {
                System.out.println("[ " + (gameMap.getCells().indexOf(c)+1) + " " + c.getName() + ": " + c.getVisitorsPiece() + " ] ");
            }

            

            nextTurn();
        }

    } //Update game state between turns
    private void nextTurn(){
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1)%players.size());
    } //Get to the next turn. Triggered by pressing next turn button.
    public void createPopup(){} //Create pop up to confirm or to get user interaction
    public void handleInfection(){
        Cell pos = currentPlayer.getPosition();
        if(pos.getName().equals("Quarantine"))
            return;
        for (Player p:pos.getVisitors()) {
            if(!p.isHealthy())
                currentPlayer.setHealth(false);
            if(!currentPlayer.isHealthy())
                p.setHealth(false);
        }
        if(pos.getClass() == Neighbourhood.class){
            Neighbourhood neighbourhood = (Neighbourhood)pos;
            currentPlayer.setHealth(neighbourhood.getCoronaRisk() < Math.random());
        }
        else if(pos.getClass() == Transportation.class){
            Transportation transportation = (Transportation) pos;
            currentPlayer.setHealth(transportation.getCoronaRisk() < Math.random());
        }

    } //Check the infection risk of the cell
    public void manageBuildings(){} //
    public void handleBankruptcy(){} //Check the bankruptcy status of players
    public void managePatients(){} //Check the patient players
    public void handleCredits(){} //Go to credits scene
    public void handleSettings(){} //Go to settings menu
    public void createPlayers(){
        for(int i = 1; i <= playerCount; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the name of player " + i + " :");
            String playerName = sc.nextLine();
            System.out.print("Please select the piece of player " + i + " " + pieces + ": ");
            String pieceName = sc.nextLine();
            pieces.remove(pieceName);
            players.add(new Player(playerName, pieceName, gameMap.getCells().get(0)));
        }
        for(int i = 1; i <= botCount; i++){
            players.add(new Bot("bot" + i, pieces.get(0) , gameMap.getCells().get(0)));
        }
        gameMap.getCells().get(0).setVisitors(players);
    } //Initialize the given amount of players
    public void createMap(){
        gameMap.newMap();
        gameMap.addCell(new StartCell());
        gameMap.addCell(new Neighbourhood("Mamak", 600, 80, 0.7, "brown" ));
        gameMap.addCell(new CardCell("Community Chest"));
        gameMap.addCell(new Neighbourhood("Sincan", 600,80, 0.65, "brown") );
        gameMap.addCell(new Taxation("Income Tax", 2000));
        gameMap.addCell(new Transportation("Esenboga Airport", 2000, 300, 0.6, "black"));
        gameMap.addCell(new Neighbourhood("Altındağ", 1000,130, 0.5, "purple"));
        gameMap.addCell(new CardCell("Chance"));
        gameMap.addCell(new Neighbourhood("Etimesgut", 1000,130, 0.55, "purple"));
        gameMap.addCell(new Neighbourhood("Beypazarı", 1200,180, 0.4, "purple"));
        gameMap.addCell(new Quarantine("Quarantine"));
        gameMap.addCell(new Neighbourhood("Gölbaşı", 1400,200, 0.8, "pink"));
        gameMap.addCell(new PublicService("Tedaş", 1500, 200, "white"));
        gameMap.addCell(new Neighbourhood("Kızılcahamam", 1400,200, 0.3, "pink"));
        gameMap.addCell(new Neighbourhood("Cebeci", 1600,220, 0.75, "pink"));
        gameMap.addCell(new Transportation("YHT", 2000, 300, 0.5, "black"));
        gameMap.addCell(new Neighbourhood("Kolej", 1800,250, 0.55, "orange"));
        gameMap.addCell(new CardCell("Community Chest"));
        gameMap.addCell(new Neighbourhood("Kızılay", 1800,250, 0.95, "orange"));
        gameMap.addCell(new Neighbourhood("Dikmen", 2000,280, 0.25, "orange"));
        gameMap.addCell(new CoronaTest("Test Center"));
        gameMap.addCell(new Neighbourhood("Emek", 2200,300, 0.55, "red"));
        gameMap.addCell(new CardCell("Chance"));
        gameMap.addCell(new Neighbourhood("Batıkent", 2200, 300, 0.45, "red" ));
        gameMap.addCell(new Neighbourhood("Yenimahalle", 2400, 320, 0.3, "red" ));
        gameMap.addCell(new Transportation("Railroads", 2000, 300, 0.9, "black"));
        gameMap.addCell(new Neighbourhood("Ostim", 2600, 350, 0.6, "yellow" ));
        gameMap.addCell(new Neighbourhood("Eryaman", 2600, 350, 0.5, "yellow" ));
        gameMap.addCell(new PublicService("ASKİ", 1500, 200, "white"));
        gameMap.addCell(new Neighbourhood("Beşevler", 2800, 380, 0.3, "yellow" ));
        gameMap.addCell(new BeInfected("Go To Quarantine"));
        gameMap.addCell(new Neighbourhood("Bahçelievler", 3000, 400, 0.45, "green" ));
        gameMap.addCell(new Neighbourhood("Ulus", 3000, 400, 0.5, "green" ));
        gameMap.addCell(new CardCell("Community Chest"));
        gameMap.addCell(new Neighbourhood("Sıhhiye", 3200, 440, 0.6, "green" ));
        gameMap.addCell(new Transportation("AŞTİ", 2000, 300, 0.9, "black"));
        gameMap.addCell(new CardCell("Chance"));
        gameMap.addCell(new Neighbourhood("Çayyalu", 3500, 460, 0.1, "blue" ));
        gameMap.addCell(new Taxation("Luxury Tax", 100));
        gameMap.addCell(new Neighbourhood("Bilkent", 4000, 500, 0.05, "blue" ));

    } //Initialize the map

    //double x, double y
    private void movePlayer(int amount){
        int position = (amount + gameMap.getCells().indexOf(currentPlayer.getPosition()))%gameMap.getCells().size();
        Cell c = gameMap.getCells().get(position);
        currentPlayer.getPosition().getVisitors().remove(currentPlayer);
        currentPlayer.setPosition(c);
        c.addVisitor(currentPlayer);
        System.out.println("Dice: " + amount);

        //moveUIPiece(x,y);
    }



    public void moveUIPiece(double x, double y){
        player_piece.setLayoutX(x);
        player_piece.setLayoutY(y);
    }

    public void playButtonPushed(javafx.event.ActionEvent event) throws IOException {

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/playGame.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void settingsButtonPushed(javafx.event.ActionEvent event) throws IOException{

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void howToPlayButtonPushed(javafx.event.ActionEvent event) throws IOException{

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/howToPlay.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void creditsButtonPushed(javafx.event.ActionEvent event) throws IOException{

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/credits.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void singlePlayerButtonPushed(javafx.event.ActionEvent event) throws IOException{

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();

        System.out.println("Başladı");
        this.startGame(5);
        this.gameFlow();
    }

    public void multiPlayerButtonPushed(javafx.event.ActionEvent event) throws IOException{

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/multi.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void backButtonPushed(javafx.event.ActionEvent event) throws IOException {

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/sample.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }

    public void gamePausedButtonPushed(javafx.event.ActionEvent event) throws IOException {

        Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/pause.fxml"));
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
    }


    public void adjustSoundButtonPushed(){


        soundControl.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

                mediaPlayer.volumeProperty().bindBidirectional( soundControl.valueProperty());
                System.out.println( (soundControl.valueProperty().getClass()) );

            }
        });
    }


}


