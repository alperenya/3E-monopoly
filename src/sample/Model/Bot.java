package sample.Model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Bot extends Player{

    //contructors
    public Bot(String name, Pane piece, Cell c){
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
