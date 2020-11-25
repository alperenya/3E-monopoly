import com.sun.org.apache.xpath.internal.operations.Bool;

public class BeInfected extends Cell {

    //properties
    private int banNumber;

    //constructers
    public BeInfected(){}

    //methods
    private Boolean infect(){
        return true;
    }
    private Boolean sendQuarantine(){
        return true;
    }
    public int getBanNumber(){
        return banNumber;
    }

    public void setBanNumber(int banNumber) {
        this.banNumber = banNumber;
    }

    public Boolean startQuarantine(){
        return true;
    }
}
