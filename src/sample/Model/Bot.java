import java.util.ArrayList;

public class Bot extends Player{

    //contructors
    Bot(String name, String piece, Cell c){
        super(name, piece, c);
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
