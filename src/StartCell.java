public class StartCell extends Cell{
    //properties
    private int startingMoney;

    //constructor
    StartCell(){}

    //methods
    public void payVisitor(){System.out.println("paranız ödendi");}

    public int getStartingMoney() {
        return startingMoney;
    }

    public void setStartingMoney(int startingMoney) {
        this.startingMoney = startingMoney;
    }
}
