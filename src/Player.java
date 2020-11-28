import java.util.ArrayList;

public class Player {

    //properties
    private String name;
    private int money;
    private String piece;
    private ArrayList<Property> properties;
    private Cell position;
    private Boolean health;
    private Boolean inQuarantine;
    private Boolean isBankrupt;
    private int banTurn;

    //constructers
    public Player(String name, String piece){
        this.name = name;
        this.piece = piece;
    }

    public Player() {
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
        return true;
    }

    public Boolean buyProperty(){
        return true;
    }

    public Boolean isInQuarantine() {
        return inQuarantine;
    }

    public void setQuarantine(Boolean inQuarantine) {
        this.inQuarantine = inQuarantine;
    }

    public Boolean buildHouse(Property p){
        return true;
    }

    public Boolean buildHospital(Property p){
        return true;
    }

    public Boolean canBuild(Property p){
        return true;
    }

    public Boolean setBankrupt(){
        return true;
    }

    public Boolean isBankrupt(){
        return isBankrupt;
    }

    public int rollDice(){
        return 0;
    }

    public Boolean mortgage(Property p){
        return true;
    }

    public Boolean cancelMortgage(){
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
