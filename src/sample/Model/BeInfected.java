package sample.Model;

import java.util.ArrayList;

/**
 * This is the class for go to quarantine(jail) slot
 */
public class BeInfected extends Cell {

    //properties
    private int banNumber;

    //constructers
    public BeInfected( ){
        this.banNumber = 3;
        this.name = "";
        this.visitors = new ArrayList<>();
    }
    public BeInfected( String name, double x, double y){
        this.name = name;
        this.visitors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }
    public BeInfected( String name, ArrayList<Player> visitors, int banNumber){
        this.name = name;
        this.visitors = visitors;
        this.banNumber = banNumber;
    }

    //methods

    /**
     * This method is used to infect a player who just landed on go to quarantine slot
     * @param player
     * @return Boolean Returns the succesion of the process
     */
    private Boolean infect( Player player ){
        try {
            player.setHealth( false );
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            return false;
        }
    }

    /**
     * Sends the player back to the quarantine slot
     * @param player
     * @param banNumber
     * @param quarantineCell
     * @return Boolean Returns the succesion of the process
     */
    private Boolean sendQuarantine( Player player, int banNumber, Cell quarantineCell ){
        try {
            player.setBanTurn( banNumber );
            player.setPosition( quarantineCell );
            return true;
        }catch (Exception e){
            System.out.println("Something went wrong.");
            return false;
        }
    }

    public int getBanNumber(){
        return banNumber;
    }

    public void setBanNumber(int banNumber) {
        this.banNumber = banNumber;
    }

    /**
     * Starts Quarantine for a player
     * @param player
     * @param banNumber
     * @param quarantineCell
     * @return Boolean Returns the succesion of the process
     */
    public Boolean startQuarantine( Player player, int banNumber, Cell quarantineCell ){
        try {
            this.infect( player );
            this.sendQuarantine( player, banNumber, quarantineCell );
            return true;
        }catch (Exception e){
            System.out.println("Something went wrong.");
            return false;
        }
    }

    /**
     * Starts Quarantine for a player
     * @param player
     * @param quarantineCell
     * @return Boolean Returns the succesion of the process
     */
    public Boolean startQuarantine( Player player, Cell quarantineCell ){
        try {
            this.infect( player );
            this.sendQuarantine( player, this.banNumber, quarantineCell );
            return true;
        }catch (Exception e){
            System.out.println("Something went wrong.");
            return false;
        }
    }
}
