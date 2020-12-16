package sample.Model;

import java.util.ArrayList;

public class CoronaTest extends Cell{

    //properties


    //constructers
    public CoronaTest(String name){
        this.name = name;
        this.visitors = new ArrayList<>();
    }
    public CoronaTest( String name, ArrayList<Player> visitors ){
        this.name = name;
        this.visitors = visitors;
    }

    //methods
    public Boolean coronaTest( ArrayList<Player> visitors, Cell quarantineCell ){
        int counter;

        try{

            for ( counter = 0; counter < visitors.size(); counter++){
                if(!visitors.get(counter).isHealthy()){
                    visitors.get(counter).setBanTurn( 2 );
                    visitors.get(counter).setPosition( quarantineCell );
                }
            }

            return true;

        }catch (Exception error){
            System.out.println("Something went wrong.");
            return false;
        }


    }

}
