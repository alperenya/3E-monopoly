package sample.Model;

import java.util.ArrayList;

/**
 * This is the class for test center
 */
public class CoronaTest extends Cell{

    //properties

    //constructers
    public CoronaTest(String name, double x, double y){
        this.name = name;
        this.visitors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    public CoronaTest( String name, ArrayList<Player> visitors ){
        this.name = name;
        this.visitors = visitors;
    }

    //methods

    /**
     * This method is used to make coronatest for the players
     * @param visitors
     * @param quarantineCell
     * @return Boolean Returns the corona state of the players
     */
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
