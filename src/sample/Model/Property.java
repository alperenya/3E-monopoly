package sample.Model;

import java.util.ArrayList;

public abstract class Property extends Cell {
    //properties
    protected Player owner;
    protected String color;
    protected int price;
    protected Boolean availability;
    protected Boolean onMortgage;

    //methods
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

    public Boolean getAvailability() { return availability; }

    public void setAvailability(Boolean availability) { this.availability = availability; }

    public Boolean getMortgage() {
        return onMortgage;
    }

    public void setMortgage(Boolean onMortgage) {
        this.onMortgage = onMortgage;
    }

    public void payOwner( int rentAmount ) {
        owner.setMoney( owner.getMoney() + rentAmount );
        System.out.println("Owner gets money: " + rentAmount );
    }

    public void retrieveRent( Player tenant, int rentAmount ) {
        tenant.setMoney( tenant.getMoney() - rentAmount );
        System.out.println("Retrieve money: " +  tenant.getName() + " from: " + rentAmount);
    }

    public abstract int calculateRent();


}
