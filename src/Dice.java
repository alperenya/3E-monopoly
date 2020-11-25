public class Dice {

    //properties
    private int value;

    //constructers
    public Dice(){}

    //methods
    public int roll(){
        return (int)( Math.random() * 6 + 1 );
    }

}
