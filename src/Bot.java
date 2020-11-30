import java.util.ArrayList;

public class Bot extends Player{

    //properties
    private String name;
    private int money;
    private String piece;
    private ArrayList<Property> properties;
    private Cell position;
    private Boolean health;
    private Boolean inQuarantine;
    private Boolean isBankrupt;
    private int banTurn;
    //contructors
    Bot(String name, String piece){
        this.name = name;
        this.piece = piece;
        this.health = false;
        this.inQuarantine = false;
        this.isBankrupt = false;
        this.banTurn = 0;
        this.properties = new ArrayList<>();
    }

    //methods
    public Boolean decideBuyingProperty(){
        return true;
    }
    public Boolean decideSellingProperty(Property p){
        return true;
    }
    public Boolean decideBuilding(Property p){
        return true;
    }
    public Boolean decideTrading(Player p){return true; }

}
