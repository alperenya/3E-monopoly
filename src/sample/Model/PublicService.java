package sample.Model;

import java.util.ArrayList;

/**
 * This is the class for publicservice slots
 */
public class PublicService extends Property{
    //properties
    private int multiplier;
    private int baseRent;

    //constructor
    public PublicService( String name, int price, int baseRent, String color, double x, double y ) {
        this.name = name;
        this.owner = null;
        this.color = color;
        this.price = price;
        this.baseRent = baseRent;
        this.availability = true;
        this.onMortgage = false;
        this.visitors = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    //methods

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getBaseRent() {
        return baseRent;
    }

    public void setBaseRent( int baseRent ){ this.baseRent = baseRent; }

    /**
     * This method is used to calculate the multiplied rent of the public service slot that differs each time by the dice roll
     * @return
     */
    @Override
    public int calculateRent() {
        int counter;
        int publicServiceAmount = 0;
        ArrayList<Property> properties = owner.getProperties();

        if ( onMortgage )
            return 0;

        for ( counter = 0; counter < properties.size(); counter++ ){
            if ( properties.get(counter) instanceof PublicService ){
                publicServiceAmount++;
            }
        }

        if ( publicServiceAmount == 2){
            return baseRent * multiplier * 2;
        }

        return baseRent * multiplier;
    }
}
