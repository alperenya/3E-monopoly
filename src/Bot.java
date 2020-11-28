public class Bot extends Player{

    //properties

    //contructers
    Bot(){}

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
