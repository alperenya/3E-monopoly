import java.sql.SQLFeatureNotSupportedException;

public class GameEngine {
    private int maxPlayers;
    private int turns;
    private int startingMoney;

    private GameUI gameUI;
    private GameMap gameMap;
    private Player[] players;
    private Player currentPlayer; //Newly added
    private Dice dice;

    public GameEngine(int maxPlayers, int startingMoney){
        this.maxPlayers = maxPlayers;
        this.startingMoney = startingMoney;
    }
    public int getMaxPlayers(){ return maxPlayers;}
    public int getTurns(){ return turns;}
    public int getStartingMoney(){ return startingMoney;}

    public GameUI getGameUI(){ return gameUI;}
    public GameMap getMap(){ return gameMap;}
    public Player[] getPlayers(){ return players;}
    public Dice getDice(){ return dice;}

    public void setMaxPlayers(int maxPlayers){ this.maxPlayers = maxPlayers;}
    public void setStartingMoney(int startingMoney){ this.startingMoney = startingMoney;}

    public void startGame(){}
    public void updateUI(){}
    public void movePlayer(){}
    public void getCurrentPlayer(){}
    public void finishGame(){}
    public void volumeUp(){}
    public void volumeDown(){}
    public void muteGame(){}
    public void manageProperties(){}
    public void gameFlow(){}
    public void nextTurn(){}
    public void createPopup(){}
    public void handleInfection(){}
    public void manageBuildings(){}
    public void handleBankruptcy(){}
    public void managePatients(){}
    public void handleCredits(){}
    public void handleSettings(){}
    public void createPlayers(){}
    public void createMap(){}
    public void createDice(){}

}
