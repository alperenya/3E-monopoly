public class Player {

    //properties
    private String name;
    private int money;
    private String piece;
    private Property properties;
    private Cell position;
    private Boolean health;
    private Boolean inQuarantine;
    private Boolean isBankrupt;
    private int banTurn;

    //constructers
    public Player(){
        System.out.println("Ben oyuncuyum");
    }

    //methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPiece(String piece) {
        this.piece = piece;
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

    public Property getProperties() {
        return properties;
    }

    public Boolean sellProperty(){
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

    public Boolean buildHouse(){
        return true;
    }

    public Boolean buildHospital(){
        return true;
    }

    public Boolean canBuild(){
        return true;
    }

    public Boolean setBankrupt(){
        return true;
    }

    public Boolean isBankrupt(){
        return isBankrupt;
    }

    public Boolean rollDice(){
        return true;
    }

    public Boolean mortgage(){
        return true;
    }

    public Boolean cancelMortgage(){
        return true;
    }

    public Boolean trade(){
        return true;
    }

    public int getBanTurn() {
        return banTurn;
    }

    public void setBanTurn(int banTurn) {
        this.banTurn = banTurn;
    }
}
