package sample.Model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the class for Cells
 */
public abstract class Cell {

    //properties
    protected String name;
    protected ArrayList<Player> visitors;
    protected double x;
    protected double y;

    //methods

    /**
     * @return String Returns the name of the Cell
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getVisitors() {
        return visitors;
    }

    public ArrayList<Pane> getVisitorsPiece(){
        ArrayList<Pane> pieces = new ArrayList<>();
       // if(visitors != null){
            for (Player p:visitors) {
                pieces.add(p.getPiece());
            }
       // }

        return pieces;
    }

    public void setVisitors( ArrayList<Player> visitors) {
        this.visitors = (ArrayList<Player>) visitors.clone();
    }

    public void addVisitor(Player p){
        visitors.add(p);
    }

    public double getX(){
        return x;
    }

    public double getY(){
     return y;
    }
}
