public abstract class Property extends Cell {
    //properties
    private Player owner;
    private String color;
    private int price;
    private Boolean availability;
    private Boolean onMortgage;

    //constructor
    Property(){}

    //methods
    public void payOwner() {System.out.println("owner gets money");}
    public abstract int calculateRent();
    public void retrieveRent() {System.out.println("retrieve money from kiracı");}
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Boolean getMortgage() {
        return onMortgage;
    }

    public void setMortgage(Boolean onMortgage) {
        this.onMortgage = onMortgage;
    }
}
