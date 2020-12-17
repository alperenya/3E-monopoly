import java.util.ArrayList;

public class Player {

    //properties
    protected String name;
    protected int money;
    protected String piece;
    protected ArrayList<Property> properties;
    protected Cell position;
    protected Boolean health;
    protected Boolean inQuarantine;
    protected Boolean isBankrupt;
    protected int banTurn;

    //constructors
    public Player(String name, String piece, Cell c){
        this.position = c;
        this.name = name;
        this.piece = piece;
        this.health = false;
        this.inQuarantine = false;
        this.isBankrupt = false;
        this.banTurn = 0;
        this.properties = new ArrayList<Property>();
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

    public String getPiece() {
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
        return true;
    }

    public boolean removeProperty(Property p){
        if(properties.contains(p)){
            properties.remove(p);
            return true;
        }

        return false;
    }

    public boolean buyProperty(Property p){
        if(money > p.getPrice()){
            properties.add(p);
            money -= p.getPrice();
            return true;
        }
        return false;
    }

    public void addProperty(Property p){
        properties.add(p);
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

    public Boolean buildHouse(Property p){
        return true;
    }

    public Boolean buildHospital(Property p){
        return true;
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
        return true;
    }

    public int getBanTurn() {
        return banTurn;
    }

    public void setBanTurn(int banTurn) {
        this.banTurn = banTurn;
    }

}
