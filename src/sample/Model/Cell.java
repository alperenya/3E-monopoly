package sample.Model;

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

    public ArrayList<String> getVisitorsPiece(){
        ArrayList<String> pieces = new ArrayList<>();
        for (Player p:visitors) {
            pieces.add(p.getPiece());
        }
        return pieces;
    }

    public void setVisitors( ArrayList<Player> visitors) {
        this.visitors = (ArrayList<Player>) visitors.clone();
    }

    public void addVisitor(Player p){
        visitors.add(p);
    }
}
