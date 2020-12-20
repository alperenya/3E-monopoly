package sample.Model;

/**
 * This is the Chance Card Class
 */
public class Chance extends Card {

    public Chance(){

    }

    public void payVisitor(Player visitor, int amount){

        visitor.setMoney( visitor.getMoney() + amount );
    }

    public void getMoneyFromUser(Player visitor, int amount){

        visitor.setMoney( visitor.getMoney() - amount );
    }

    public void getMoneyFromUser(Player visitor, double rate){

        int newBalance = visitor.getMoney() - (int)(visitor.getMoney() * rate);
        visitor.setMoney( newBalance );
    }
    public void vaccinate(Player visitor){
        visitor.setHealth( true );
        visitor.setBanTurn( 0 );
    }
    public void makeInfected( Player visitor ){
        visitor.setHealth(false);
    }

}
