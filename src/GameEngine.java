import java.util.ArrayList;

public class GameEngine {
    private int maxPlayers; //Will be decided after pressing create game button
    private int botCount; //Newly added. Will be decided after pressing create game button
    private int turns; //Will be set to zero at the start of the game
    private int startingMoney; //Will be decided after pressing create game button
    private float gameVolume; //Current game volume. Will be 50% initially.

    private GameUI gameUI; //Reference to gameui component
    private GameMap gameMap; //Reference to the gamemap component
    private ArrayList<Player> players; //Reference to all players
    private Player currentPlayer; //Newly added. Current player of the turn
    private Dice dice; //Reference to the dice

    public GameEngine(){} //Default constructor
    public int getMaxPlayers(){ return maxPlayers;}
    public int getTurns(){ return turns;}
    public int getStartingMoney(){ return startingMoney;}

    public GameUI getGameUI(){ return gameUI;}
    public GameMap getMap(){ return gameMap;}
    public ArrayList<Player> getPlayers(){ return players;}
    public Dice getDice(){ return dice;}
    public Player getCurrentPlayer(){return currentPlayer;}

    public void setMaxPlayers(int maxPlayers){ this.maxPlayers = maxPlayers;}
    public void setBotCount(int botCount){ this.botCount = botCount;}
    public void setStartingMoney(int startingMoney){ this.startingMoney = startingMoney;}

    //These methods will be working inside of the engine so I think they can be changed to private.
    public void startGame(int maxPlayers, int startingMoney){ //Runs after deciding maxPlayers and
        this.maxPlayers = maxPlayers;
        this.startingMoney = startingMoney;
        //Class properties will be initialized here
    }
    public void updateUI(){} //Update game ui between turns
    public void movePlayer(Cell toCell){} //Move player to a cell
    public void finishGame(){} //Finish the game if everybody is bankrupt except one
    public void volumeUp(){} //Increase the volume by a unit
    public void volumeDown(){} //Decrease the volume  by a unit
    public void muteGame(){} //Make the volume 0
    public void manageProperties(){} //
    public void gameFlow(){} //Update game state between turns
    public void nextTurn(){} //Get to the next turn. Triggered by pressing next turn button.
    public void createPopup(){} //Create pop up to confirm or to get user interaction
    public void handleInfection(){} //Check the infection risk of the cell
    public void manageBuildings(){} //
    public void handleBankruptcy(){} //Check the bankruptcy status of players
    public void managePatients(){} //Check the patient players
    public void handleCredits(){} //Go to credits scene
    public void handleSettings(){} //Go to settings menu
    public void createPlayers(int count){} //Initialize the given amount of players
    public void createMap(){} //Initialize the map
    public void createDice(){} //Initialize the dice

}
