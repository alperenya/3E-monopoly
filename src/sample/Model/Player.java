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
    protected String password;
    protected int infectionTurn;



    //constructors
    public Player(String name, Pane piece, String password, Cell c){
        this.position = c;
        this.name = name;
        this.piece = piece;
        this.health = true;
        this.inQuarantine = false;
        this.isBankrupt = false;
        this.banTurn = 0;
        this.properties = new ArrayList<>();
        this.password = password;
        this.infectionTurn = 0;
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

    public String getPassword() {
        return password;
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

    public ArrayList<Property> canBuild(){
        ArrayList canBuild = new ArrayList<String>();
        ArrayList canBuildings = new ArrayList<Property>();

        //Check for brown
        int brownCount = 0;
        int purpleCount = 0;
        int blueCount = 0;
        int darkBlueCount = 0;
        int yellowCount = 0;
        int orangeCount = 0;
        int redCount = 0;
        int greenCount = 0;

        for(Property p: properties){
            if(p instanceof Neighbourhood){
                if(p.getColor().equals("brown")){
                    brownCount++;
                }else if(p.getColor().equals("purple")){
                    purpleCount++;
                }else if(p.getColor().equals("blue")){
                    blueCount++;
                }else if(p.getColor().equals("darkblue")){
                    darkBlueCount++;
                }else if(p.getColor().equals("yellow")){
                    yellowCount++;
                }else if(p.getColor().equals("orange")){
                    orangeCount++;
                }else if(p.getColor().equals("red")){
                    redCount++;
                }else if(p.getColor().equals("green")){
                    greenCount++;
                }
            }
        }

        if(brownCount == 2){
            canBuild.add("brown");
        }else if(purpleCount == 3){
            canBuild.add("purple");
        }else if(blueCount == 3){
            canBuild.add("blue");
        }else if(darkBlueCount == 2){
            canBuild.add("darkblue");
        }else if(yellowCount == 3){
            canBuild.add("yellow");
        }else if(orangeCount == 3){
            canBuild.add("orange");
        }else if(redCount == 3){
            canBuild.add("red");
        }else if(greenCount == 3){
            canBuild.add("green");
        }

        for(Property p: properties){
            if(canBuild.contains(p.getColor())){
                canBuildings.add(p);
            }
        }

        return canBuildings;
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
        money += p.getPrice()*0.5;
        return true;
    }

    public Boolean cancelMortgage(Property p){
        p.setMortgage(false);
        money -= p.getPrice()*0.55;
        return true;
    }

    //Will do the trading without any checks.
    public void trade(Player otherPlayer, int offeredMoney, Property offeredProperty, int requestedMoney, Property requestedProperty){

        if(offeredProperty != null){
            properties.remove(offeredProperty);
            otherPlayer.addProperty(offeredProperty);
        }
        money -= offeredMoney;
        otherPlayer.setMoney(otherPlayer.getMoney() + offeredMoney);

        if(requestedProperty != null){
            otherPlayer.removeProperty(requestedProperty);
            properties.add(requestedProperty);
        }
        otherPlayer.setMoney(otherPlayer.getMoney() - requestedMoney);
        money += requestedMoney;
    }

    public int getBanTurn() {
        return banTurn;
    }

    public void setBanTurn(int banTurn) {
        this.banTurn = banTurn;
    }

    public int getInfectionTurn(){
        return infectionTurn;
    }

    public void setInfectionTurn( int turn ){
        infectionTurn = 0;
    }

}
