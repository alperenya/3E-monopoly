import java.util.ArrayList;

public class BeInfected extends Cell {

    //properties
    private int banNumber;

    //constructers
    public BeInfected( ){
        this.banNumber = 3;
        this.name = "";
        this.visitors = new ArrayList<>();
    }
    public BeInfected( String name){
        this.name = name;
        this.visitors = new ArrayList<>();
    }
    public BeInfected( String name, ArrayList<Player> visitors, int banNumber){
        this.name = name;
        this.visitors = visitors;
        this.banNumber = banNumber;
    }

    //methods
    private Boolean infect( Player player ){
        try {
            player.setHealth( false );
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            return false;
        }
    }
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
