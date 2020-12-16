package sample.Model;

import java.util.ArrayList;

public class Card extends Cell{
    //properties
    private String message;
    private String cardFunction; //Newly added

    //constructers
    public Card( String name){
        this.name = name;
        this.visitors = new ArrayList<Player>();
        this.message = "";
        this.cardFunction = "";
    }
    public Card( String name, ArrayList<Player> visitors){
        this.name = name;
        this.visitors = visitors;
        this.message = "";
        this.cardFunction = "";
    }
    public Card( String name, ArrayList<Player> visitors, String message, String cardFunction){
        this.name = name;
        this.visitors = visitors;
        this.message = message;
        this.cardFunction = cardFunction;
    }

    //methods
    private String getMessage(){ return message;}
    private String getType(){ return this.name;}
    private String getCardFunction(){ return cardFunction;}

    public void setMessage(String message){ this.message = message;}
    public void setType(String type){ this.name = type;}
    public void setCardFunction(String cardFunction){ this.cardFunction = cardFunction;}

    public void executeCardFunction(){} //Newly added. Will check cardFunction and execute corresponding function

    //Card functions
    private void payVisitor(Player visitor, int amount){
        visitor.setMoney( visitor.getMoney() + amount );
    }
    private void getMoneyFromUser(Player visitor, int amount){
        visitor.setMoney( visitor.getMoney() + amount );
    }
    private void getMoneyFromUser(Player visitor, double rate){

        int newBalance = visitor.getMoney() - (int)(visitor.getMoney() * rate);
        visitor.setMoney( newBalance );
    }
    private void vaccinate(Player visitor){
        visitor.setHealth( true );
        visitor.setBanTurn( 0 );
    }
}
