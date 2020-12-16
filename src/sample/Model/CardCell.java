package sample.Model;

import java.util.ArrayList;

public class CardCell extends Cell{

    //properties
    private String type;

    //constructers
    public CardCell( String name ){
        this.name = name;
        this.type = "";
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
