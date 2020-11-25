public abstract class Property extends Cell {
    private Player owner;
    private String color;
    private int price;
    private Boolean availability;
    private Boolean onMortgage;

    public int payOwner(){
    }
    public abstract int calculateRent();
    public abstract int retrieveRent();

}
