import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameEngine {
    private final int MAX_PLAYERS = 6; //Will be decided after pressing create game button
    private final int STARTING_MONEY = 20000;
    private int playerCount;
    private int botCount; //Newly added. Will be decided after pressing create game button
    private int turns; //Will be set to zero at the start of the game
    private ArrayList<String> pieces = new ArrayList<>(Arrays.asList("car", "hamburger", "glass", "computer", "football", "hat"));
    private float gameVolume; //Current game volume. Will be 50% initially.

    private GameUI gameUI; //Reference to gameui component
    private GameMap gameMap; //Reference to the gamemap component
    private ArrayList<Player> players; //Reference to all players
    private Player currentPlayer; //Newly added. Current player of the turn
    private Dice dice; //Reference to the dice

    public GameEngine(){} //Default constructor
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
        createDice();
        createPlayers();
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
    public void createPlayers(){
        players = new ArrayList<>();
        for(int i = 1; i <= playerCount; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter the name of player " + i + " :");
            String playerName = sc.nextLine();
            System.out.print("Please select the piece of player " + i + " " + pieces + ": ");
            String pieceName = sc.nextLine();
            pieces.remove(pieceName);
            Player p = new Player(playerName, pieceName);
            players.add(p);
        }
        for(int i = 1; i <= botCount; i++){
            Player b = new Bot("bot" + i, pieces.get(0));
            players.add(b);
            System.out.println(b.getName());
        }


        System.out.println(players.get(1).getName());
    } //Initialize the given amount of players
    public void createMap(){} //Initialize the map
    public void createDice(){} //Initialize the dice




}
