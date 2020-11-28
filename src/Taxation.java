import java.util.ArrayList;

public class Taxation extends Cell {
    //properties
    private double taxRate;

    //constructers
    public Taxation() {
        this.name = "";
        this.visitors = new ArrayList<Player>();
        this.taxRate = 0.23;
    }
    public Taxation( int taxRate ) {
        this.name = "";
        this.visitors = new ArrayList<Player>();
        this.taxRate = taxRate;
    }
    public Taxation( String name, ArrayList<Player> visitors) {
        this.name = name;
        this.visitors = visitors;
        this.taxRate = 0.23;
    }
    public Taxation( String name, ArrayList<Player> visitors, int taxRate) {
        this.name = name;
        this.visitors = visitors;
        this.taxRate = taxRate;
    }

    //methods
    public void getMoneyFromUser() {

        int counter;

        for ( counter = 0; counter < visitors.size(); counter++){

            Player visitor = visitors.get(counter);
            int taxtAmount = visitor.getMoney() - (int)(visitor.getMoney() * taxRate);
            visitor.setMoney( taxtAmount );

            System.out.println("Player: " + visitor.getName() + " has payed: " + taxtAmount );
        }
    }
}