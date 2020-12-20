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

    /**
     * Sets the name of the Cell
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to get the visitors of a specific sell
     * @return ArrayList<Player> Returns the list of players on a cell
     */
    public ArrayList<Player> getVisitors() {
        return visitors;
    }

    /**
     * This method is used to get the individuals Piece
     * @return ArrayList<Pane> Returns the list of pieces
     */
    public ArrayList<Pane> getVisitorsPiece(){
        ArrayList<Pane> pieces = new ArrayList<>();
            for (Player p:visitors) {
                pieces.add(p.getPiece());
            }
        return pieces;
    }

    /**
     * This method is used to set the vitors of a cell
     * @param visitors
     */
    public void setVisitors( ArrayList<Player> visitors) {
        this.visitors = (ArrayList<Player>) visitors.clone();
    }

    /**
     * This method is used to add visitor to an cell
     * @param addedPlayer
     */
    public void addVisitor(Player addedPlayer){
        visitors.add(addedPlayer);
    }

    public double getX(){
        return x;
    }

    public double getY(){
     return y;
    }
}
