package sample.Model;

import java.util.ArrayList;

/**
 * This is the taxation class that manages the taxes from the players
 */
public class Taxation extends Cell {
    //properties
    private double taxRate;
    private final int LUXURY_TAX_AMOUNT = 150;

    //constructers
    public Taxation() {
        this.name = "";
        this.visitors = new ArrayList<>();
        this.taxRate = 0.23;
    }
    public Taxation( String name, double taxRate, double x, double y ) {
        this.name = name;
        this.visitors = new ArrayList<>();
        this.taxRate = taxRate;
        this.x = x;
        this.y = y;
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

    /**
     * This method is used to extract tax money from the players
     * @param player
     */
    public void getMoneyFromUser(Player player) {

        /*int counter;

        for ( counter = 0; counter < visitors.size(); counter++){*/

            //Player visitor = visitors.get(counter);
            if(this.name.equals("Income Tax")) {
                int taxAmount = player.getMoney() - (int) (player.getMoney() * taxRate);
                player.setMoney(taxAmount);
                System.out.println("Player: " + player.getName() + " has payed: " + taxAmount );
            }

            if(this.name.equals("Luxury Tax")) {
                //int taxAmount = player.getMoney() - (int) (player.getMoney() * taxRate);
                player.setMoney(player.getMoney() - LUXURY_TAX_AMOUNT);
                System.out.println("Player: " + player.getName() + " has payed: " + LUXURY_TAX_AMOUNT );
            }


        //}
    }
}