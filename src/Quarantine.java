public class Quarantine extends Cell{

    //properties
    private String message;
    private Player[] patients;

    //constructers
    public Quarantine(){}

    //methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPatients(Player[] patients) {
        this.patients = patients;
    }

    public Player[] getPatients() {
        return patients;
    }
}
