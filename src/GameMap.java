import java.util.Hashtable;

public class GameMap {
    private Cell[] cells;
    private Card[] cards;
    private Hashtable colors;

    public GameMap(){}
    public Cell[] getCells(){ return cells;}
    public Card[] getCards(){ return cards;} //Was not on the diagram. Newly added
    public Hashtable getColors(){ return colors;} //Was not on the diagram. Newly added

    public Cell[] getSameColoredProperties(){ return null;}
    public Player[] getSickPlayers(){ return null;} //getPatientsNames changed to getSickPlayers

    public void addCell(){}
    public void addCards(){}
    public void drawCard(){}
    public void shuffleCards(){}

}
