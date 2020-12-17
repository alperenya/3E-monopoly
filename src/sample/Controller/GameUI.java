package sample.Controller;

import sample.Model.*;
import java.util.ArrayList;

public class GameUI {
    public GameUI(){} //Default constructor
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
    public void buyProperty(Player currentPlayer, Property property){} //After current player buys a property
    public void sellProperty(Player currentPlayer, Property property){} //After current player sells a property
    public void pauseGame(){} //Pause the game in the current state
    public void celebrateWinner(Player winner){ System.out.println("winner.getName() + has won the game"); } //Activate the celebration animation for a given player
    public void buildHouse(Player currentPlayer, Property property){} //After current player builds a house
    public void buildHospital(Player currentPlayer, Property property){} //After current player builds a hospital
    public void mortgageProperty(Player currentPlayer, Property property){} //After current player mortgages a property
}
