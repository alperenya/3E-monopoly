package sample.Model;

import java.util.ArrayList;

public class CardCell extends Cell{

    //properties
    private String type;

    //constructers
    public CardCell( String name, double x , double y ){
        this.name = name;
        this.type = "";
        this.x = x;
        this.y = y;
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
