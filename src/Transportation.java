import java.util.ArrayList;

public class Transportation extends Property {
    //properties
    private String type;
    private int rent;
    private double coronaRisk;

    //constructor
    public Transportation( int price, int rent, double coronaRisk, String color ) {
        this.owner = null;
        this.color = color;
        this.price = price;
        this.rent = rent;
        this.coronaRisk = coronaRisk;
        this.availability = true;
        this.onMortgage = false;
    }

    //methods
    public int getRent() { return rent; }

    public void setRent( int rent ){ this.rent = rent; }

    public double getCoronaRisk() { return coronaRisk; }

    public void setCoronaRisk( double coronaRisk) { this.coronaRisk = coronaRisk; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public int calculateRent() {
        int counter;
        int transportationAmount = 0;
        ArrayList<Property> properties = owner.getProperties();

        for ( counter = 0; counter < properties.size(); counter++ ){
            if ( properties.get(counter) instanceof Transportation ){
                transportationAmount++;
            }
        }

        if ( transportationAmount == 2 ){

            return rent * 2;

        }else if ( transportationAmount == 3 ){

            return (int)(rent * 2.5);

        }else if ( transportationAmount == 4 ){

            return rent * 3;

        }

        return rent;
    }
}
