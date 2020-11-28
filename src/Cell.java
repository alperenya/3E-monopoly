import java.util.ArrayList;

public abstract class Cell {

    //properties
    protected String name;
    protected ArrayList<Player> visitors;

    //methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getVisitors() {
        return visitors;
    }

    public void setVisitors( ArrayList<Player> visitors) {

        this.visitors = visitors;
    }
}
