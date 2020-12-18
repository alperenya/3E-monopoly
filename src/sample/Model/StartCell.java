package sample.Model;

import java.util.ArrayList;

public class StartCell extends Cell{
    //properties
    private final int STARTINGMONEY = 200;

    //constructor
    public StartCell( double x, double y ){
        this.name = "Start";
        this.visitors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    public StartCell( String name, ArrayList<Player> visitors ){
        this.name = name;
        this.visitors = visitors;
    }

    //methods
    public void payVisitors(Player player){
       // int counter;

        //for ( counter = 0; counter < visitors.size(); counter++){

            //layer visitor = visitors.get(player);
            player.setMoney( player.getMoney() + STARTINGMONEY );

            System.out.println("Paying Succesfull to the player: " + player.getName() );
       // }
    }

}
