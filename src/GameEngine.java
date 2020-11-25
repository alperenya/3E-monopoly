import java.sql.SQLFeatureNotSupportedException;

public class GameEngine {
    private int maxPlayers;
    private int turns;
    private int startingMoney;

    private GameUI gameUI;
    private GameMap gameMap;
    private Player[] players;
    private Player currentPlayer; //Added
    private Dice dice;

    public GameEngine(){}
    public int getMaxPlayers(){ return maxPlayers;}
    public int getTurns(){ return turns;}
    public int getStartingMoney(){ return startingMoney;}

    public GameUI getGameUI(){ return gameUI;}
    public GameMap getMap(){ return gameMap;}
    public Player[] getPlayers(){ return players;}
    public Dice getDice(){ return dice;}

    private void startGame(){}
    private void updateUI(){}
    private void movePlayer(){}
    private void getCurrentPlayer(){}
    private void finishGame(){}
    private void volumeUp(){}
    private void volumeDown(){}
    private void muteGame(){}
    private void manageProperties(){}
    private void gameFlow(){}
    private void nextTurn(){}
    private void createPopup(){}
    private void handleInfection(){}
    private void manageBuildings(){}
    private void handleBankruptcy(){}
    private void managePatients(){}
    private void handleCredits(){}
    private void handleSettings(){}
    private void createPlayers(){}
    private void createMap(){}
    private void createDice(){}

}
