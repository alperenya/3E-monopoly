import java.util.ArrayList;

public class PublicService extends Property{
    //properties
    private String type;
    private int multiplier;
    private int baseRent;

    //constructor
    public PublicService( int price, int baseRent, String color, String type ) {
        this.owner = null;
        this.color = color;
        this.price = price;
        this.baseRent = baseRent;
        this.availability = true;
        this.onMortgage = false;
    }

    //methods
    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getRent() {
        return baseRent;
    }

    public void setRent( int baseRent ){ this.baseRent = baseRent; }

    @Override
    public int calculateRent() {
        int counter;
        int publicServiceAmount = 0;
        ArrayList<Property> properties = owner.getProperties();

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
