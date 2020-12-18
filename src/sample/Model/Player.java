package sample.Model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Player {

    //properties
    protected String name;
    protected int money;
    protected Pane piece;
    protected ArrayList<Property> properties;
    protected Cell position;
    protected Boolean health;
    protected Boolean inQuarantine;
    protected Boolean isBankrupt;
    protected int banTurn;

    //constructors
    public Player(String name, Pane piece, Cell c){
        this.position = c;
        this.name = name;
        this.piece = piece;
        this.health = true;
        this.inQuarantine = false;
        this.isBankrupt = false;
        this.banTurn = 0;
        this.properties = new ArrayList<>();
    }

    public Player(){
    }

    //methods
    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Pane getPiece() {
        return piece;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public Boolean isHealthy(){
        return health;
    }

    public void setHealth(Boolean health) {
        this.health = health;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public Boolean sellProperty(Property p){
        properties.remove(p);
        money += p.getPrice();
        //p.setAvailability(true);
        return true;
    }

    public boolean removeProperty(Property p){
        if(properties.contains(p)){
            properties.remove(p);
            p.setAvailability(true);
            return true;
        }

        return false;
    }

    public boolean buyProperty(Property p){
        if( money > p.getPrice() && p.getAvailability() ){
            properties.add(p);
            p.setAvailability(false);
            p.setOwner(this);
            money -= p.getPrice();
            return true;
        }
        return false;
    }

    public void addProperty(Property p){
        properties.add(p);
        p.setAvailability(false);
    }

    public Boolean isInQuarantine() {
        return inQuarantine;
    }

    public void setQuarantine(Boolean inQuarantine) {
        this.inQuarantine = inQuarantine;
    }

    public Boolean canBuild(Property p){
       /* if(money >= p.getPrice() && p.getAvailability()){
            return true;
        }
        */
        return false;
    }

    public Boolean buildHouse(Neighbourhood n){
        if(n.getHouseCount() <= 3){
            n.setHouseCount(n.getHouseCount() + 1);
            return true;
        }

        return false;
    }

    public Boolean buildHospital(Neighbourhood n){
        if(n.getHouseCount() > 3){
            n.setHouseCount(n.getHouseCount() + 1);
            return true;
        }

        return false;
    }

    public Boolean setBankrupt(){
        if(money <= 0){
            isBankrupt = true;
            return true;
        }

        isBankrupt = false;
        return false;
    }

    public Boolean isBankrupt(){
        return isBankrupt;
    }

    public Boolean mortgage(Property p){
        p.setMortgage(true);
        money += p.getPrice()*0.7;
        return true;
    }

    public Boolean cancelMortgage(Property p){
        p.setMortgage(true);
        money -= p.getPrice()*0.7;
        return true;
    }

    public Boolean trade(Player p){
        // WHICH PROPERTIES WILL BE EXCHANGED IS NOT STATED?
        // THIS IS A TEST CODE
        Commerce commerce = new Commerce(this,p,properties.get(0), p.getProperties().get(0));
        commerce.exchange();
        return true;
    }

    public int getBanTurn() {
        return banTurn;
    }

    public void setBanTurn(int banTurn) {
        this.banTurn = banTurn;
    }

}
