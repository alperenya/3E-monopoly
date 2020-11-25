public abstract class Cell {

    //properties
    private String name;
    private  Player[] visitors;

    //methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player[] getVisitors() {
        return visitors;
    }

    public void setVisitors(Player[] visitors) {
        this.visitors = visitors;
    }
}
