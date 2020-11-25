public class PublicService extends Property{
    //properties
    private String type;
    private int multiplier;

    //constructor

    public PublicService() {
    }


    //methods

    @Override
    public int calculateRent() {
        return 0;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
