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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public float getCoronaRisk() {
        return coronaRisk;
    }

    public void setCoronaRisk(float coronaRisk) {
        this.coronaRisk = coronaRisk;
    }
}
