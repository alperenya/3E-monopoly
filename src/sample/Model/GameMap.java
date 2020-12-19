package sample.Model;


import java.util.*;

public class GameMap {
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
    public ArrayList<Cell> getCells(){ return cells;} //Get all cells on the map

    public ArrayList<CommunityChest> getCommunityCards() {return communityCards; }

    public ArrayList<Chance> getChanceCards() { return chanceCards; }

    public ArrayList<Player> getPatients(){ return cells.get(10).getVisitors();} //return players in the quarantine

    public void addCell(Cell cell){ //to add all cells before starting the game
        cells.add(cell);
    }

    public void newMap(){
        cells = new ArrayList<>();
        chanceCards = new ArrayList<>();
        communityCards = new ArrayList<>();
    }

    public Boolean addCards(Card card){ //to add all cards before starting the game
        if(card.getClass() == Chance.class)
            chanceCards.add((Chance) card);
        else if(card.getClass() == CommunityChest.class)
            communityCards.add((CommunityChest) card);
        else
            return false;
        return true;
    }
    public Card drawCard(String type){ //draw cards when a player comes to a card cell
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
    public void shuffleCards(){//String type){ //shuffle card before starting the game
        Collections.shuffle(chanceCards);
        Collections.shuffle(communityCards);
    }

}
