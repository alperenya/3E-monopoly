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

//import javax.smartcardio.Card;
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
    @FXML private Pane card_container;
    @FXML private Label card_title;
    @FXML private Label card_text;
    @FXML private Button skipbtn;
    @FXML private Label diceLabel;
    @FXML private Button buyButton;
    @FXML private Label turnlabel;
    @FXML private Button rollDice;
    @FXML private Button mortgageButton;
    @FXML private Button upgradeButton;

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

    //Build Popup elements
    @FXML private Button buildButton;
    @FXML private Button cancelButton;
    @FXML private Label houseCount;
    @FXML private Label hospitalCount;
    @FXML private ComboBox buildPropeties;

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
        }
    } // Updates the Property section

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
            if(currentPlayer.buyProperty( (Property) currentPosition )){

                updateUI();
                updateMoneyUI();
            }
        }
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
            updateMoneyUI();
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
            tradePopup.close();
        });
        cancelTradeButton.setOnAction(eventCancelTrade -> {
            // get a handle to the stage
            Stage stage = (Stage) cancelTradeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        });
    }

    public void gameFlow(){
        card_container.setVisible(false);
        card_text.setWrapText(true);
        card_title.setWrapText(true);

        skipbtn.setOnAction(event -> {
            nextTurn();

            if(currentPlayer.getBanTurn() > 0){
                currentPlayer.setBanTurn(currentPlayer.getBanTurn() - 1);
                nextTurn();
            }

            rollDice.setDisable(false);
            buyButton.setDisable(false);
            card_container.setVisible(false);

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

        upgradeButton.setOnAction(event -> {
            try {
                manageBuildings(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buyButton.setOnAction(event -> {

            manageProperties();
        });

        rollDice.setOnAction( event -> {

            movePlayer(dice.roll());
            rollDice.setDisable(true);
            Cell currentPosition = currentPlayer.getPosition();

            if ( currentPosition instanceof PublicService  && ((PublicService)currentPosition).hasOwner() ){

                int diceValue = dice.roll();

                ((PublicService) currentPosition).setMultiplier( diceValue );
                int rent = ((PublicService) currentPosition).calculateRent();


                currentPlayer.setMoney( currentPlayer.getMoney() - rent );

                Player owner = ((PublicService) currentPosition).getOwner();
                owner.setMoney(owner.getMoney() + rent);

                updateMoneyUI();
                diceLabel.setText( "Dice: " + dice.roll() );
            }
            else if(currentPosition instanceof Taxation){

                buyButton.setDisable(true);

                ((Taxation) currentPosition).getMoneyFromUser(currentPlayer);

                updateMoneyUI();

            }else if(currentPosition instanceof StartCell){

                buyButton.setDisable(true);

                ((StartCell) currentPosition).payVisitors(currentPlayer);

                updateMoneyUI();

            }else if( currentPosition instanceof CardCell ){
                buyButton.setDisable(true);

                if(((CardCell) currentPosition).getType().equals("chance") ){
                    Card randomCard = gameMap.drawCard("Chance");
                    card_title.setText("Chance Card");
                    card_text.setText(randomCard.getMessage());

                    if(randomCard.getCardFunction().equals("payVisitor")){
                        randomCard.payVisitor(currentPlayer,200);
                    }else if(randomCard.getCardFunction().equals("getMoney")){
                        randomCard.getMoneyFromUser(currentPlayer,200);
                    }else if(randomCard.getCardFunction().equals("getPercent")){
                        randomCard.getMoneyFromUser(currentPlayer,0.10);
                    }else if(randomCard.getCardFunction().equals("vaccinate")){
                        randomCard.vaccinate(currentPlayer);
                    }else if(randomCard.getCardFunction().equals("infect")){
                        randomCard.makeInfected(currentPlayer);
                    }
                }

                if(((CardCell) currentPosition).getType().equals("community") ){
                    Card randomCard = gameMap.drawCard("CommunityChest");
                    card_title.setText("Community Chest Card");
                    card_text.setText(randomCard.getMessage());

                    if(randomCard.getCardFunction().equals("payVisitor")){
                        randomCard.payVisitor(currentPlayer,200);
                    }else if(randomCard.getCardFunction().equals("getMoney")){
                        randomCard.getMoneyFromUser(currentPlayer,200);
                    }else if(randomCard.getCardFunction().equals("getPercent")){
                        randomCard.getMoneyFromUser(currentPlayer,0.10);
                    }else if(randomCard.getCardFunction().equals("vaccinate")){
                        randomCard.vaccinate(currentPlayer);
                    }else if(randomCard.getCardFunction().equals("infect")){
                        randomCard.makeInfected(currentPlayer);
                    }
                }

                updateMoneyUI();
                card_container.setVisible(true);
            }else if( currentPosition instanceof Neighbourhood && ((Neighbourhood)currentPosition).hasOwner() ){

                int rent = ((Neighbourhood) currentPosition).calculateRent();
                currentPlayer.setMoney( currentPlayer.getMoney() - rent );

                Player owner = ((Neighbourhood) currentPosition).getOwner();
                owner.setMoney(owner.getMoney() + rent);

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

            if(startCellPassed){

                startCellPassed = false;
                currentPlayer.setMoney(currentPlayer.getMoney() + 200);

                updateMoneyUI();

            }

            handleInfection();
            managePatients();
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

            if ( virusExist ){
                for ( Player player : currentPosition.getVisitors() ) {
                    player.setHealth( false );
                }
            }

        }


        if( currentPosition instanceof Neighbourhood ){
            Neighbourhood neighbourhood = (Neighbourhood)currentPosition;
            if( !(neighbourhood.getCoronaRisk() < Math.random()) ){
                currentPlayer.setHealth( false );
                currentPlayer.setInfectionTurn( turns );
            }
        }
        else if( currentPosition instanceof Transportation ){
            Transportation transportation = (Transportation) currentPosition;
            if( !(transportation.getCoronaRisk() < Math.random()) ){
                currentPlayer.setHealth( false );
                currentPlayer.setInfectionTurn( turns );
            }
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
                updateHealth.setText( updateLabel );
            }

            counter++;

        }
    } //Check the infection risk of the cell

    public void manageBuildings(javafx.event.ActionEvent event) throws IOException{
        Stage buildingPopup = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/buildhouse.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        buildingPopup.setScene(new Scene(root));

        buildingPopup.initModality(Modality.APPLICATION_MODAL);
        buildingPopup.initOwner(tradeButton.getScene().getWindow());
        buildingPopup.show();

        for (Property p:currentPlayer.canBuild()) {
            buildPropeties.getItems().add( p.getName() );
        }

        cancelButton.setOnAction( event1 -> {
            buildingPopup.close();
        });

        buildButton.setOnAction( event1 -> {

            String house = (String) buildPropeties.getSelectionModel().getSelectedItem();
            Neighbourhood selectedProperty = null;

            for( Property p : currentPlayer.getProperties() ){
                if( p.getName().equals( house ) ){
                    selectedProperty = ( Neighbourhood ) p;
                }
            }

            if( currentPlayer.buildHouse( selectedProperty ) ){
                updateMoneyUI();
            }

        });

    } //


    public void handleBankruptcy(){} //Check the bankruptcy status of players
    public void managePatients(){

        for( Player player :  players){

            if ( (turns == player.getInfectionTurn() + 3) && !player.isInQuarantine() && !player.isHealthy() ){
                player.setBanTurn( 3 );
                moveUIPiece(player.getPiece(),65,735);
                player.setPosition(gameMap.getCells().get(10));
                player.getPosition().addVisitor(player);
            }
        }

    } //Check the patient players

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
        gameMap.addCell(new Neighbourhood("Altındağ", 1000,130, 0.5, "brown", 655, 755 ));
        gameMap.addCell(new CardCell("Community Chest","community", 590, 755 ));
        gameMap.addCell(new Neighbourhood("Sincan", 600,80, 0.65, "brown", 525, 755 ) );
        gameMap.addCell(new Taxation("Income Tax", 0.23, 460, 755 ));
        gameMap.addCell(new Transportation("Railroad", 2000, 300, 0.9, "black", 395, 755 ));
        gameMap.addCell(new Neighbourhood("Kolej", 1800,250, 0.55, "blue", 330, 755 ));
        gameMap.addCell(new CardCell("Chance","chance", 265, 755 ));
        gameMap.addCell(new Neighbourhood("Beşevler", 2800, 380, 0.3, "blue" , 195, 755 ));
        gameMap.addCell(new Neighbourhood("Çayyolu", 3500, 460, 0.1, "blue" , 135, 755 ));
        gameMap.addCell(new Quarantine("Quarantine" , 65, 730 ));
        gameMap.addCell(new Neighbourhood("Eryaman", 2600, 350, 0.5, "purple" , 40, 655 ));
        gameMap.addCell(new PublicService("TEDAŞ", 1500, 200, "white", 40, 590 ));
        gameMap.addCell(new Neighbourhood("Ostim", 2600, 350, 0.6, "purple" , 40, 525 ));
        gameMap.addCell(new Neighbourhood("Beypazarı", 1200,180, 0.4, "purple", 40, 460 ));
        gameMap.addCell(new Transportation("YHT", 2000, 300, 0.5, "black", 40, 395));
        gameMap.addCell(new Neighbourhood("Dikmen", 2000,280, 0.25, "orange", 40, 325 ));
        gameMap.addCell(new CardCell("Community Chest","community", 40, 265 ));
        gameMap.addCell(new Neighbourhood("Etimesgut", 1000,130, 0.55, "orange", 40, 195 ));
        gameMap.addCell(new Neighbourhood("Gölbaşı", 1400,200, 0.8, "orange", 40, 130 ));
        gameMap.addCell(new CoronaTest("Test Center" , 50, 50 ));
        gameMap.addCell(new Neighbourhood("Batıkent", 2200, 300, 0.45, "red" , 135, 35 ));
        gameMap.addCell(new CardCell("Chance","chance", 195, 35 ));
        gameMap.addCell(new Neighbourhood("Yenimahalle", 2400, 320, 0.3, "red", 260, 35  ));
        gameMap.addCell(new Neighbourhood("Mamak", 600, 80, 0.7, "red", 330, 35 ));
        gameMap.addCell(new Transportation("Esenboga Airport", 2000, 300, 0.6, "black", 395, 35 ));
        gameMap.addCell(new Neighbourhood("Sıhhiye", 3200, 440, 0.6, "yellow", 460, 35  ));
        gameMap.addCell(new Neighbourhood("Emek", 2200,300, 0.55, "yellow", 525, 35 ));
        gameMap.addCell(new PublicService("ASKİ", 1500, 200, "white", 590, 35 ));
        gameMap.addCell(new Neighbourhood("Bilkent", 4000, 500, 0.05, "yellow" , 655, 35 ));
        gameMap.addCell(new BeInfected("Go To Quarantine", 755, 35));
        gameMap.addCell(new Neighbourhood("Bahçelievler", 3000, 400, 0.45, "green", 755, 135  ));
        gameMap.addCell(new Neighbourhood("Cebeci", 1600,220, 0.75, "green", 755, 200 ));
        gameMap.addCell(new CardCell("Community Chest","community", 755, 260 ));
        gameMap.addCell(new Neighbourhood("Ulus", 3000, 400, 0.5, "green" , 755, 325 ));
        gameMap.addCell(new Transportation("AŞTİ", 2000, 300, 0.9, "black", 755, 395 ));
        gameMap.addCell(new CardCell("Chance","chance", 755, 460 ));
        gameMap.addCell(new Neighbourhood("Kızılcahamam", 1400,200, 0.3, "darkblue", 755, 525 ));
        gameMap.addCell(new Taxation("Luxury Tax", 0.23, 755, 590 ));
        gameMap.addCell(new Neighbourhood("Kızılay", 1800,250, 0.95, "darkblue", 755, 655 ));

        Chance payVisitorsCard = new Chance();
        payVisitorsCard.setMessage("Bank Pays 200 TL to the Visitor.");
        payVisitorsCard.setCardFunction("payVisitor");

        Chance getMoneyCard = new Chance();
        getMoneyCard.setMessage("Visitor pays 200 TL to the Bank.");
        getMoneyCard.setCardFunction("getMoney");

        Chance getPercentCard = new Chance();
        getPercentCard.setMessage("Visitor pays 10% of their money to the Bank.");
        getPercentCard.setCardFunction("getPercent");

        Chance vaccinateCard = new Chance();
        vaccinateCard.setMessage("Player is now vaccinated and healthy.");
        vaccinateCard.setCardFunction("vaccinate");

        Chance infectCard = new Chance();
        infectCard.setMessage("Player is now infected and not healthy.");
        infectCard.setCardFunction("infect");

        gameMap.addCards(payVisitorsCard);
        gameMap.addCards(getMoneyCard);
        gameMap.addCards(getPercentCard);
        gameMap.addCards(vaccinateCard);
        gameMap.addCards(infectCard);

        CommunityChest payVisitorsCardCommunity = new CommunityChest();
        payVisitorsCardCommunity.setMessage("Bank Pays 200 TL to the Visitor.");
        payVisitorsCardCommunity.setCardFunction("payVisitor");

        CommunityChest getMoneyCardCommunity = new CommunityChest();
        getMoneyCardCommunity.setMessage("Visitor pays 200 TL to the Bank.");
        getMoneyCardCommunity.setCardFunction("getMoney");

        CommunityChest getPercentCardCommunity = new CommunityChest();
        getPercentCardCommunity.setMessage("Visitor pays 10% of their money to the Bank.");
        getPercentCardCommunity.setCardFunction("getPercent");

        CommunityChest vaccinateCardCommunity = new CommunityChest();
        vaccinateCardCommunity.setMessage("Player is now vaccinated and healthy.");
        vaccinateCardCommunity.setCardFunction("vaccinate");

        CommunityChest infectCardCommunity = new CommunityChest();
        infectCardCommunity.setMessage("Player is now infected and not healthy.");
        infectCardCommunity.setCardFunction("infect");

        gameMap.addCards(payVisitorsCardCommunity);
        gameMap.addCards(getMoneyCardCommunity);
        gameMap.addCards(getPercentCardCommunity);
        gameMap.addCards(vaccinateCardCommunity);
        gameMap.addCards(infectCardCommunity);

        gameMap.shuffleCards();
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

    Stage window;
    public Scene gameScene;
    public Scene pauseScene;

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
        //if(gameScene == null){
            //Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/game.fxml"));
            loader.setController(this);
            Parent settingsParent = (Parent) loader.load();
            //Scene settingsScene = new Scene( settingsParent );
            gameScene = new Scene(settingsParent);

            window = (Stage)((Node)event.getSource()).getScene().getWindow();

            //window.setScene(settingsScene);
            window.setScene(gameScene);
            window.show();
        System.out.println("start: " + loader.getController().toString());
            this.startGame(1);
            this.gameFlow();
    }

    public void continueButtonPushed(){//Scene scene
        window.setScene(gameScene);
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

    //@FXML private Button pauseContinueButton;
    public void gamePausedButtonPushed(javafx.event.ActionEvent event) throws IOException {

        //Parent settingsParent = FXMLLoader.load(getClass().getResource("../view/pause.fxml"));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/pause.fxml"));
        loader.setController(this);
        //loader.getController();

        Parent settingsParent = (Parent) loader.load();
        pauseScene = new Scene( settingsParent );

        window.setScene(pauseScene);
        window.show();

        //continueButtonPushed(gameScene);
       //pauseContinueButton.setOnAction(event1 ->{
         //   continueButtonPushed(gameScene);
        //});


        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/pause.fxml"));
        Stage stage = new Stage();
        stage.initOwner(pauseButton.getScene().getWindow());
        stage.setScene(new Scene((Parent) loader.load()));

        // showAndWait will block execution until the window closes...

        stage.showAndWait();*/

        //window.setScene(pauseScene);
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


