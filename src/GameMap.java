import javafx.scene.control.Cell;

import java.util.ArrayList;
import java.util.Hashtable;

public class GameMap {
    private ArrayList<Cell> cells; //Will be initialized when start game button is pressed
    private ArrayList<Card> cards; //Will be initialized when start game button is pressed
    private Hashtable<Cell, String> colors; //Will be initialized when start game button is pressed

    public GameMap(){ //Will be called after start game button is pressed
        cells = new ArrayList<Cell>();
        cards = new ArrayList<Card>();
        colors = new Hashtable<Cell, String>();
    }
    public ArrayList<Cell> getCells(){ return cells;} //Get all cells on the map
    public ArrayList<Card> getCards(){ return cards;} //Newly added. Return all the cards
    public Hashtable<Cell, String> getColors(){ return colors;} //Newly added. Return all the colors

    public ArrayList<Cell> getSameColoredProperties(){ return null;} //Get the properties with the same color
    public ArrayList<Player> getSickPlayers(){ return null;} //getPatientsNames changed to getSickPlayers

    public Boolean addCell(Cell cell){return true;}
    public Boolean addCards(Card card){return true;}
    public Card drawCard(String type){return null;}
    public void shuffleCards(String type){}

}
