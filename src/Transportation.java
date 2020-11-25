public class Transportation extends Property {
    //properties
    private String type;
    private int rent;
    private float coronaRisk;

    //constructor
    public Transportation() {
    }

    //methods
    @Override
    public int calculateRent() {
        return 0;
    }

    public int getRent() {
        return rent;
    }

    public float getCoronaRisk() {
        return coronaRisk;
    }

    public void setCoronaRisk(float coronaRisk) {
        this.coronaRisk = coronaRisk;
    }
}
