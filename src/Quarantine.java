import java.util.ArrayList;

public class Quarantine extends Cell{

    //properties
    private String message;
    private ArrayList<Player> patients;

    //constructers
    public Quarantine(){
        this.message = "Unfornutanelty, Your Covid-19 Test is POSITIVE";
        patients = new ArrayList<Player>();
    }
    public Quarantine( String message, ArrayList<Player> patients){
        this.message = message;
        this.patients = patients;
    }

    //methods
    public String getMessage() {

        return message;
    }

    public void setMessage( String message) {

        this.message = message;
    }

    public void setPatients( ArrayList<Player> patients) {

        this.patients = patients;
    }

    public ArrayList<Player> getPatients() {
        return patients;
    }
}
