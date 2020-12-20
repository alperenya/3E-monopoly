package sample.Model;

import java.util.ArrayList;

public class Neighbourhood extends Property {
    //properties
    private int houseCount;
    private int rent;
    private double coronaRisk;

    //constructor
    public Neighbourhood( String name, int price, int rent, double coronaRisk, String color, double x, double y ){
        this.name = name;
        this.owner = null;
        this.color = color;
        this.price = price;
        this.houseCount = 0;
        this.rent = rent;
        this.coronaRisk = coronaRisk;
        this.availability = true;
        this.onMortgage = false;
        this.visitors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    //methods
    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public int getRent() {
        return rent;
    }

    public void setRent( int rent ){ this.rent = rent; }

    public double getCoronaRisk() {
        return coronaRisk;
    }

    public void setCoronaRisk( double coronaRisk ) {
        this.coronaRisk = coronaRisk;
    }

    public int calculateRent( ) {

        if ( onMortgage )
            return 0;

        if ( houseCount == 1 ){

            return (int)(rent * 1.3);

        }else if ( houseCount == 2 ){

            return (int)(rent * 1.6);

        }else if ( houseCount == 3 ){

            return rent * 2;

        }else if ( houseCount == 4 ){

            return rent * 3;

        }

        return rent;
    }
}
