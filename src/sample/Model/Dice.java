package sample.Model;

public class Dice {

    //properties
    private int value;

    //constructers
    public Dice(){}

    //methods
    public int roll(){
        int dice = (int)( Math.random() * 12 + 1);
        return dice == 1 ? 2 : dice;
    }

}
