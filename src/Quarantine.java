import java.util.ArrayList;

public class Quarantine extends Cell{

    //properties
    private String message;
    private ArrayList<Player> patients;

    //constructers
    public Quarantine(String name){
        this.name = name;
        this.message = "Unfornutanelty, Your Covid-19 Test is POSITIVE";
        patients = new ArrayList<>();
        this.visitors = new ArrayList<>();
    }
    public Quarantine( String name, String message, ArrayList<Player> patients){
        this.name = name;
        this.message = message;
        this.patients = patients;
        this.visitors = new ArrayList<>();
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
