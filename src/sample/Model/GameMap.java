package sample.Model;


import java.util.*;

/**
 * This is the class for the gamemap
 * @author Group3E
 */
public class GameMap implements Iterator{

    private static GameMap instance;
    private ArrayList<Cell> cells; //Will be initialized when start game button is pressed
    private ArrayList<CommunityChest> communityCards;
    private ArrayList<Chance> chanceCards; //Will be initialized when start game button is pressed

    public static GameMap getInstance(){
        if (instance == null){
            instance = new GameMap();
        }
        return instance;
    }

    private GameMap(){ //Will be called after start game button is pressed
        cells = new ArrayList<>();
        chanceCards = new ArrayList<>();
        communityCards = new ArrayList<>();
    }

    /**
     * This methods is used to get all of the cells on the map.
     * @return ArrayList<Cell> Returns the cells
     */
    public ArrayList<Cell> getCells(){ return cells;}

    /**
     * This method is used to get the Community Chest Cards.
     * @return ArrayList<CommunityChest> Returns the Community Cards
     */
    public ArrayList<CommunityChest> getCommunityCards() {return communityCards; }

    /**
     * This method is used to get the Chance Cards.
     * @return ArrayList<Chance> Returns the chance cards
     */
    public ArrayList<Chance> getChanceCards() { return chanceCards; }

    /**
     * This method is used to get the players who are in quarantine
     * @return ArrayList<Player> Returns the players who are in the quarantine
     */
    public ArrayList<Player> getPatients(){ return cells.get(10).getVisitors();}

    /**
     * This method adds cell to the map
     * @param cell
     */
    public void addCell(Cell cell){
        cells.add(cell);
    }

    /**
     * This method generates a new map
     */
    public void newMap(){
        cells = new ArrayList<>();
        chanceCards = new ArrayList<>();
        communityCards = new ArrayList<>();
    }

    /**
     * This method places all of the cards into the gameboard before the game starts
     * @param card
     * @return Boolean This returns the succesion of the process.
     */
    public Boolean addCards(Card card){
        if(card.getClass() == Chance.class)
            chanceCards.add((Chance) card);
        else if(card.getClass() == CommunityChest.class)
            communityCards.add((CommunityChest) card);
        else
            return false;
        return true;
    }

    /**
     * This method is used to draw a card when a player lands on a card slot
     * @param type
     * @return Card Retunrs the drawen card
     */
    public Card drawCard(String type){
        Card c;
        if(type.equals("Chance")){
            c = chanceCards.get(0);
            chanceCards.remove(0);
            chanceCards.add((Chance) c);
        }
        else if(type.equals("CommunityChest")){
            c = communityCards.get(0);
            communityCards.remove(0);
            communityCards.add((CommunityChest) c);
        }
        else{
            c = null;
        }

        return c;
    }

    /**
     * This method shuffles the cards before starting the game
     */
    public void shuffleCards(){//String type){ 

        Collections.shuffle(chanceCards);
        Collections.shuffle(communityCards);

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object Next() {
        return null;
    }
}
