package sample.Model;

import java.util.ArrayList;

public class StartCell extends Cell{
    //properties
    private final int STARTINGMONEY = 2000;

    //constructor
    public StartCell( ){
        this.name = "Start";
        this.visitors = new ArrayList<>();
    }
    public StartCell( String name, ArrayList<Player> visitors ){
        this.name = name;
        this.visitors = visitors;
    }

    //methods
    public void payVisitors( ){
        int counter;

        for ( counter = 0; counter < visitors.size(); counter++){

            Player visitor = visitors.get(counter);
            visitor.setMoney( visitor.getMoney() + STARTINGMONEY );

            System.out.println("Paying Succesfull to the player: " + visitor.getName() );
        }
    }

}
