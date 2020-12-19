package sample.Model;

import java.util.ArrayList;

public abstract class Card{
    //properties
    private String message;
    private String function;

    // abstract class has no constructers

    //methods
    public String getMessage(){ return message;}
    public String getCardFunction(){ return function;}

    public void setMessage(String message){ this.message = message;}
    public void setCardFunction(String cardFunction){ this.function = cardFunction;}

    public abstract void payVisitor(Player visitor, int amount);
    public abstract void getMoneyFromUser(Player visitor, int amount);
    public abstract void getMoneyFromUser(Player visitor, double rate);
    public abstract void vaccinate(Player visitor);
    public abstract void makeInfected( Player visitor );

}
