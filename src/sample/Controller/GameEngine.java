package sample.Controller;

//import com.sun.deploy.util.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Model.*;
import sample.Model.Cell;
//import sun.plugin2.ipc.windows.WindowsNamedPipe;
//import sun.security.jca.GetInstance;

import javax.smartcardio.Card;
//import javax.xml.soap.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GameEngine {
    //Properties
    @FXML
    private Button closeButton;
    private MediaPlayer mediaPlayer;
    @FXML private Slider soundControl;
    @FXML private Pane player_piece;
    @FXML private Pane player_piece_1;
    @FXML private Pane player_piece_2;
    @FXML private Pane player_piece_3;
    @FXML private Pane player_piece_4;
    @FXML private Pane player_piece_5;
    @FXML private Button skipbtn;
    @FXML private Label diceLabel;
    @FXML private Button buyButton;
    @FXML private Label turnlabel;
    @FXML private Button rollDice;
    @FXML private Button mortgageButton;

    //Trade popup elements
    @FXML private Button tradeButton;

    @FXML private GridPane property;
    @FXML private GridPane healthSystem;
    @FXML private GridPane money_grid;
    @FXML private GridPane name_grid;

    @FXML private Button completeTradeButton;
    @FXML private Button cancelTradeButton;
    @FXML private TextField offeredMoneyBox;
    @FXML private TextField requestedMoneyBox;
    @FXML private PasswordField buyerPasswordBox;
    @FXML private PasswordField sellerPasswordBox;
    @FXML private ListView buyerPropertiesListView;
    @FXML private ListView sellerPropertiesListView;
    @FXML private ComboBox clientsComboBox;

    //MOrtgage popup elements
    @FXML private Button mortgageAcceptButton;
    @FXML private Button mortgageCancelButton;
    @FXML private Label totalMortgageEarnLabel;
    @FXML private Label totalMortgagePayLabel;
    @FXML private ListView propertiesListView;
    @FXML private ListView mortgagedPropertiesListView;

    private final int MAX_PLAYERS = 6; //Will be decided after pressing create game button
    private final int STARTING_MONEY = 100000;
    private final int MAX_BAN_TURN = 3;
    private int playerCount;
    private int botCount; //Newly added. Will be decided after pressing create game button
    private int turns; //Will be set to zero at the start of the game
    private ArrayList<Pane> pieces;
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
        pieces = null;
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
        /*Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the number of bot: ");*/
        setBotCount(MAX_PLAYERS-playerCount);

        createMap();
        createPlayers();
        currentPlayer = players.get(0);
        turnlabel.setText("Round: " +  currentPlayer.getName());
        System.out.println("Piece 1 id = " + player_piece_1.getId());
    }
    public void updateUI(){
        for (Node node : property.getChildren()) {
            Label updateProperty =  (Label) node ;

            for( Player player : players ){

                for( Property p : player.getProperties() ){

                    if ( updateProperty.getText().contains( p.getName() ) ){
                        String updateLabel = p.getName() + " -> " + player.getName();
                        updateProperty.setText( updateLabel );
                    }

                }

            }

            System.out.println(updateProperty.getText());
        }
    }
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

    public boolean manageProperties(){

        Cell currentPosition = currentPlayer.getPosition();

        if( currentPosition instanceof Property ){

            int price = ((Property) currentPlayer.getPosition()).getPrice();
            if(currentPlayer.buyProperty( (Property) currentPosition )){

                updateUI();

                updateMoneyUI();
            }
            //gameUI.buyProperty( currentPlayer, (Property) currentPosition );

            return true;
        }
        return false;
    }

    private void openMortgagePopup(javafx.event.ActionEvent event) throws IOException {
        Stage mortgagePopup = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mortgage.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        mortgagePopup.setScene(new Scene(root));

        mortgagePopup.initModality(Modality.APPLICATION_MODAL);
        mortgagePopup.initOwner(tradeButton.getScene().getWindow());
        mortgagePopup.show();

        for (Property p:currentPlayer.getProperties()) {
            if (!p.getMortgage()){
                propertiesListView.getItems().add(p.getName());
            }
            else{
                mortgagedPropertiesListView.getItems().add(p.getName());
            }
        }
        propertiesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Property p: currentPlayer.getProperties()) {
                    if(p.getName().equals(propertiesListView.getSelectionModel().getSelectedItem())){
                        totalMortgageEarnLabel.setText((int)(Integer.parseInt(totalMortgageEarnLabel.getText()) + p.getPrice()*0.5) + "");
                        break;
                    }
                }
                mortgagedPropertiesListView.getItems().add(propertiesListView.getSelectionModel().getSelectedItem());
                propertiesListView.getItems().remove(propertiesListView.getSelectionModel().getSelectedItem());
            }
        });
        mortgagedPropertiesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Property p: currentPlayer.getProperties()) {
                    if(p.getName().equals(mortgagedPropertiesListView.getSelectionModel().getSelectedItem())){
                        totalMortgagePayLabel.setText((int)(Integer.parseInt(totalMortgagePayLabel.getText()) + p.getPrice()*0.55) + "");
                        break;
                    }
                }
                propertiesListView.getItems().add(mortgagedPropertiesListView.getSelectionModel().getSelectedItem());
                mortgagedPropertiesListView.getItems().remove(mortgagedPropertiesListView.getSelectionModel().getSelectedItem());
            }
        });

        mortgageAcceptButton.setOnAction(event1 -> {
            for (Property p:currentPlayer.getProperties()) {
                if(p.getMortgage()){
                    if (propertiesListView.getItems().contains(p.getName())){
                        currentPlayer.cancelMortgage(p);
                    }
                }
                else{
                    if (mortgagedPropertiesListView.getItems().contains(p.getName())){
                        currentPlayer.mortgage(p);
                    }
                }
            }
            // get a handle to the stage
            Stage stage = (Stage) mortgageAcceptButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        });
        mortgageCancelButton.setOnAction(event1 -> {
            // get a handle to the stage
            Stage stage = (Stage) mortgageCancelButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        });
    }

    private void openTradePopup(javafx.event.ActionEvent event) throws IOException {
        Stage tradePopup = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/trade.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        tradePopup.setScene(new Scene(root));

        tradePopup.initModality(Modality.APPLICATION_MODAL);
        tradePopup.initOwner(tradeButton.getScene().getWindow());
        tradePopup.show();

        for (Property p:currentPlayer.getProperties()) {
            buyerPropertiesListView.getItems().add(p.getName());
        }
        for (Player p:players) {
            if (!p.isBankrupt() && p != currentPlayer){
                clientsComboBox.getItems().add(p.getName());
            }
        }

        clientsComboBox.setOnAction(eventComboBox -> {
            Player client = null;
            String clientName = (String) clientsComboBox.getSelectionModel().getSelectedItem();
            for (Player p:players) {
                if (p.getName().equals(clientName)){
                    client = p;
                    break;
                }
            }
            sellerPropertiesListView.getItems().clear();
            for (Property p:client.getProperties()) {
                sellerPropertiesListView.getItems().add(p.getName());
            }
        });

        completeTradeButton.setOnAction(eventCompleteTrade -> {
            Player client = null;
            String clientName = (String) clientsComboBox.getSelectionModel().getSelectedItem();
            for (Player p:players) {
                if (p.getName().equals(clientName)){
                    client = p;
                    break;
                }
            }
            if (client == null) return;
            if(buyerPasswordBox.getText().equals(currentPlayer.getPassword()) && sellerPasswordBox.getText().equals(client.getPassword())){
                Property offeredProperty = null;
                if (buyerPropertiesListView.getSelectionModel().getSelectedItem() != null){
                    for (Property p:currentPlayer.getProperties()) {
                        if (p.getName().equals((String) buyerPropertiesListView.getSelectionModel().getSelectedItem())){
                            offeredProperty = p;
                            break;
                        }
                    }
                }

                Property requestedProperty = null;
                if (sellerPropertiesListView.getSelectionModel().getSelectedItem() != null) {
                    for (Property p : client.getProperties()) {
                        if (p.getName().equals((String) sellerPropertiesListView.getSelectionModel().getSelectedItem())) {
                            requestedProperty = p;
                            break;
                        }
                    }
                }

                Commerce commerce = new Commerce(currentPlayer, client, offeredProperty, requestedProperty,
                        Integer.parseInt(isNumeric(offeredMoneyBox.getText()) ? offeredMoneyBox.getText() : "0"), Integer.parseInt(isNumeric(requestedMoneyBox.getText()) ? requestedMoneyBox.getText() : "0"));
                if (commerce.exchange()){
                    updateUI();
                    System.out.println("Exchange successfull");
                    updateMoneyUI();
                }
                else{
                    System.out.println("Excahnge failed");
                }
            }
            else{
                System.out.println("Wrong password");
            }
        });
        cancelTradeButton.setOnAction(eventCancelTrade -> {
            // get a handle to the stage
            Stage stage = (Stage) cancelTradeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        });
    }

    public void gameFlow(){

        skipbtn.setOnAction(event -> {
            nextTurn();

            if(currentPlayer.getBanTurn() > 0){
                currentPlayer.setBanTurn(currentPlayer.getBanTurn() - 1);
                nextTurn();
            }

            rollDice.setDisable(false);
            buyButton.setDisable(false);

        });

        tradeButton.setOnAction(event -> {

            try {
                openTradePopup(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mortgageButton.setOnAction(event -> {

            try {
                openMortgagePopup(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buyButton.setOnAction(event -> {
            if ( manageProperties() ){
                for ( Property pro :  currentPlayer.getProperties() ){
                    System.out.println( pro.getName() + " -> " + pro.getPrice() );
                }
                System.out.println( currentPlayer.getMoney() );
            }
        });

        rollDice.setOnAction( event -> {
            movePlayer(dice.roll());
            rollDice.setDisable(true);
            Cell currentPosition = currentPlayer.getPosition();

            if ( currentPosition instanceof PublicService  && ((PublicService)currentPosition).hasOwner() ){

                int diceValue = dice.roll();

                ((PublicService) currentPosition).setMultiplier( diceValue );
                int rent = ((PublicService) currentPosition).calculateRent();

                //System.out.println( "Retrieved money: " + rent );

                currentPlayer.setMoney( currentPlayer.getMoney() - rent );

                Player owner = ((PublicService) currentPosition).getOwner();
                owner.setMoney(owner.getMoney() + rent);

                System.out.println( "CurrentPlayer money: " + currentPlayer.getMoney() );
                updateMoneyUI();
                diceLabel.setText( "Dice: " + dice.roll() );
            }
            else if(currentPosition instanceof Taxation){
                buyButton.setDisable(true);
                ((Taxation) currentPosition).getMoneyFromUser(currentPlayer);
                updateMoneyUI();
                System.out.println( currentPlayer.getName() + ": " + currentPlayer.getMoney());
            }else if(currentPosition instanceof StartCell){
                buyButton.setDisable(true);
                ((StartCell) currentPosition).payVisitors(currentPlayer);
                updateMoneyUI();
                System.out.println( currentPlayer.getName() + ": " + currentPlayer.getMoney());
            }else if(startCellPassed){
                startCellPassed = false;
                currentPlayer.setMoney(currentPlayer.getMoney() + 200);
                updateMoneyUI();
            }else if( currentPosition instanceof CardCell ){
                buyButton.setDisable(true);
            }else if( currentPosition instanceof Neighbourhood && ((Neighbourhood)currentPosition).hasOwner() ){
                //int diceValue = dice.roll();

                int rent = ((Neighbourhood) currentPosition).calculateRent();

                //System.out.println( "Retrieved money: " + rent );

                currentPlayer.setMoney( currentPlayer.getMoney() - rent );

                Player owner = ((Neighbourhood) currentPosition).getOwner();
                owner.setMoney(owner.getMoney() + rent);
                System.out.println( "CurrentPlayer money: " + currentPlayer.getMoney() );
                updateMoneyUI();
                diceLabel.setText( "Dice: " + dice.roll() );
            }else if( currentPosition instanceof BeInfected){
                moveUIPiece(currentPlayer.getPiece(),65,735);
                currentPlayer.setPosition(gameMap.getCells().get(10));
                currentPlayer.getPosition().addVisitor(currentPlayer);
            }else if( currentPosition instanceof CoronaTest ){
                if ( !currentPlayer.isHealthy() ){
                    moveUIPiece(currentPlayer.getPiece(),65,735);
                    currentPlayer.setPosition(gameMap.getCells().get(10));
                    currentPlayer.getPosition().addVisitor(currentPlayer);
                }
                currentPlayer.setBanTurn(MAX_BAN_TURN);
            }

            handleInfection();
        });


    } //Update game state between turns


    public void nextTurn(){
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1)%players.size());
        turnlabel.setText("Round: " +  currentPlayer.getName());
        turns++;
    } //Get to the next turn. Triggered by pressing next turn button.
    public void createPopup(){} //Create pop up to confirm or to get user interaction
    public void handleInfection(){

        Cell currentPosition = currentPlayer.getPosition();
        boolean virusExist = false;

        if(currentPosition.getName().equals("Quarantine"))
            return;

        if ( currentPosition.getVisitors().size() > 1 ){

            for ( Player player : currentPosition.getVisitors() ) {

                if ( !player.isHealthy() ){
                    virusExist = true;
                    break;
                }
            }
            System.out.println( virusExist );
            if ( virusExist ){
                for ( Player player : currentPosition.getVisitors() ) {
                    player.setHealth( false );
                }
            }

        }


        if( currentPosition instanceof Neighbourhood ){
            Neighbourhood neighbourhood = (Neighbourhood)currentPosition;
            if( !(neighbourhood.getCoronaRisk() < Math.random()) )
                currentPlayer.setHealth( false );
            System.out.println( currentPlayer.getName() + " neig " + neighbourhood.getCoronaRisk() + " " + currentPlayer.isHealthy() );
        }
        else if( currentPosition instanceof Transportation ){
            Transportation transportation = (Transportation) currentPosition;
            if( !(transportation.getCoronaRisk() < Math.random()) )
                currentPlayer.setHealth( false );

            System.out.println( currentPlayer.getName() + " trnas " + transportation.getCoronaRisk() + " " + currentPlayer.isHealthy());
        }

        int counter = 0;


        for (Node node : healthSystem.getChildren()) {
            Label updateHealth =  (Label) node ;

            Player player = players.get(counter);

            if( updateHealth.getText().contains( player.getName() ) ){

                String updateLabel = player.getName() + "                     ";

                if ( player.isInQuarantine() ){
                    updateLabel = updateLabel + "Quarantine";
                }else if( !player.isHealthy() ){
                    updateLabel = updateLabel + "Infected";
                }else{
                    updateLabel = updateLabel + "Healhty";
                }
                System.out.println( updateLabel );
                updateHealth.setText( updateLabel );
            }

            counter++;

        }


    } //Check the infection risk of the cell
    public void manageBuildings(){} //
    public void handleBankruptcy(){} //Check the bankruptcy status of players
    public void managePatients(){} //Check the patient players
    public void handleCredits(){} //Go to credits scene
    public void handleSettings(){} //Go to settings menu

    public void updateMoneyUI(){
        int playerCounter = 0;

        for (Node node : money_grid.getChildren()) {
            Label label = (Label) node;

            label.setText(players.get(playerCounter).getMoney() + "");
            playerCounter++;
        }

        playerCounter = 0;

        for (Node node : name_grid.getChildren()) {
            Label label = (Label) node;

            label.setText(players.get(playerCounter).getName() + "");
            playerCounter++;
        }

    }

    public void createPlayers(){
        //for(int i = 1; i <= playerCount; i++){
            //Scanner sc = new Scanner(System.in);
            //System.out.print("Please enter the name of player " + i + " :");
            //String playerName = sc.nextLine();
            //System.out.print("Please select the piece of player " + i + " " + pieces + ": ");
            //String pieceName = sc.nextLine();
            pieces = new ArrayList<>(Arrays.asList(player_piece,player_piece_1,player_piece_2,player_piece_3,player_piece_4,player_piece_5));
            pieces.remove(player_piece);
            players.add(new Player("playerName", player_piece, "1234", gameMap.getCells().get(0)));
        //}
        for(int i = 1; i <= botCount; i++){
            players.add(new Bot("bot" + i, pieces.get(0) , "1234", gameMap.getCells().get(0)));
            pieces.remove(pieces.get(0));
        }

        for(int i = 0; i <= players.size() - 1; i++){
            players.get(i).setMoney(STARTING_MONEY);
            moveUIPiece(players.get(i).getPiece(),gameMap.getCells().get(0).getX(),gameMap.getCells().get(0).getY());
        }


        updateMoneyUI();
        gameMap.getCells().get(0).setVisitors(players);
    } //Initialize the given amount of players
    public void createMap(){
        gameMap.newMap();
        gameMap.addCell(new StartCell(735,735));
        gameMap.addCell(new Neighbourhood("Altındağ", 1000,130, 0.5, "purple", 655, 755 ));
        gameMap.addCell(new CardCell("Community Chest", 590, 755 ));
        gameMap.addCell(new Neighbourhood("Sincan", 600,80, 0.65, "brown", 525, 755 ) );
        gameMap.addCell(new Taxation("Income Tax", 0.23, 460, 755 ));
        gameMap.addCell(new Transportation("Railroad", 2000, 300, 0.9, "black", 395, 755 ));
        gameMap.addCell(new Neighbourhood("Kolej", 1800,250, 0.55, "orange", 330, 755 ));
        gameMap.addCell(new CardCell("Chance", 265, 755 ));
        gameMap.addCell(new Neighbourhood("Beşevler", 2800, 380, 0.3, "yellow" , 195, 755 ));
        gameMap.addCell(new Neighbourhood("Çayyolu", 3500, 460, 0.1, "blue" , 135, 755 ));
        gameMap.addCell(new Quarantine("Quarantine" , 65, 730 ));
        gameMap.addCell(new Neighbourhood("Eryaman", 2600, 350, 0.5, "yellow" , 40, 655 ));
        gameMap.addCell(new PublicService("TEDAŞ", 1500, 200, "white", 40, 590 ));
        gameMap.addCell(new Neighbourhood("Ostim", 2600, 350, 0.6, "yellow" , 40, 525 ));
        gameMap.addCell(new Neighbourhood("Beypazarı", 1200,180, 0.4, "purple", 40, 460 ));
        gameMap.addCell(new Transportation("YHT", 2000, 300, 0.5, "black", 40, 395));
        gameMap.addCell(new Neighbourhood("Dikmen", 2000,280, 0.25, "orange", 40, 325 ));
        gameMap.addCell(new CardCell("Community Chest", 40, 265 ));
        gameMap.addCell(new Neighbourhood("Etimesgut", 1000,130, 0.55, "purple", 40, 195 ));
        gameMap.addCell(new Neighbourhood("Gölbaşı", 1400,200, 0.8, "pink", 40, 130 ));
        gameMap.addCell(new CoronaTest("Test Center" , 50, 50 ));
        gameMap.addCell(new Neighbourhood("Batıkent", 2200, 300, 0.45, "red" , 135, 35 ));
        gameMap.addCell(new CardCell("Chance", 195, 35 ));
        gameMap.addCell(new Neighbourhood("Yenimahalle", 2400, 320, 0.3, "red", 260, 35  ));
        gameMap.addCell(new Neighbourhood("Mamak", 600, 80, 0.7, "brown", 330, 35 ));
        gameMap.addCell(new Transportation("Esenboga Airport", 2000, 300, 0.6, "black", 395, 35 ));
        gameMap.addCell(new Neighbourhood("Sıhhiye", 3200, 440, 0.6, "green", 460, 35  ));
        gameMap.addCell(new Neighbourhood("Emek", 2200,300, 0.55, "red", 525, 35 ));
        gameMap.addCell(new PublicService("ASKİ", 1500, 200, "white", 590, 35 ));
        gameMap.addCell(new Neighbourhood("Bilkent", 4000, 500, 0.05, "blue" , 655, 35 ));
        gameMap.addCell(new BeInfected("Go To Quarantine", 755, 35));
        gameMap.addCell(new Neighbourhood("Bahçelievler", 3000, 400, 0.45, "green", 755, 135  ));
        gameMap.addCell(new Neighbourhood("Cebeci", 1600,220, 0.75, "pink", 755, 200 ));
        gameMap.addCell(new CardCell("Community Chest", 755, 260 ));
        gameMap.addCell(new Neighbourhood("Ulus", 3000, 400, 0.5, "green" , 755, 325 ));
        gameMap.addCell(new Transportation("AŞTİ", 2000, 300, 0.9, "black", 755, 395 ));
        gameMap.addCell(new CardCell("Chance", 755, 460 ));
        gameMap.addCell(new Neighbourhood("Kızılcahamam", 1400,200, 0.3, "pink", 755, 525 ));
        gameMap.addCell(new Taxation("Luxury Tax", 0.23, 755, 590 ));
        gameMap.addCell(new Neighbourhood("Kızılay", 1800,250, 0.95, "orange", 755, 655 ));
    } //Initialize the map

    private  boolean startCellPassed = false;
    //double x, double y
    private void movePlayer(int amount){
        int position = (amount + gameMap.getCells().indexOf(currentPlayer.getPosition()));

        if(position >= gameMap.getCells().size()){
            startCellPassed = true;
        }

        position= position%gameMap.getCells().size();
        Cell c = gameMap.getCells().get(position);
        currentPlayer.getPosition().getVisitors().remove(currentPlayer);
        currentPlayer.setPosition(c);
        System.out.println(c.getName());
        c.addVisitor(currentPlayer);
        System.out.println("Dice: " + amount);

        diceLabel.setText( "Dice: " + amount );

        moveUIPiece(currentPlayer.getPiece(),c.getX(),c.getY());
    }

    public void moveUIPiece(Pane piece,double x, double y){
        piece.setLayoutX(x);
        piece.setLayoutY(y);
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

        //Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
        GameEngine engine = new GameEngine();
        loader.setController(this);
        Parent settingsParent = (Parent) loader.load();
        Scene settingsScene = new Scene( settingsParent );

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(settingsScene);
        window.show();
        System.out.println("Başladı: " + property);
        this.startGame(1);
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

    private boolean isNumeric(final String str) {

        // null or empty
        if (str == "" || str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}


