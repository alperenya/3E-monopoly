package sample.Model;

import java.util.ArrayList;

/**
 * This is the cardcell class
 */
public class CardCell extends Cell{

    //properties
    private String type;

    //constructers
    public CardCell( String name, String type, double x , double y ){
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.visitors = new ArrayList<>();
    }
    public CardCell(String name, ArrayList<Player> visitors){
        this.name = name;
        this.type = "";
        visitors = this.getVisitors();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
