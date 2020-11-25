public class Neighbourhood extends Property {
    //properties
    private int houseCount;
    private int rent;
    private float coronaRisk;

    //constructor
    Neighbourhood(){}

    //methods
    @Override
    public int calculateRent() {
        return 0;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
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
