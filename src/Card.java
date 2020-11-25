public class Card {
    private String message;
    private String type;
    private String cardFunction; //Newly added

    public Card(String message, String type, String cardFunction){
        this.message = message;
        this.type = type;
        this.cardFunction = cardFunction;
    }
    private String getMessage(){ return message;}
    private String getType(){ return type;}
    private String getCardFunction(){ return cardFunction;}

    public void setMessage(String message){ this.message = message;}
    public void setType(String type){ this.type = type;}
    public void setCardFunction(String cardFunction){ this.cardFunction = cardFunction;}

    public void executeCardFunction(){} //Newly added. Will check cardFunction and execute corresponding function

    //Card functions
    private void payVisitor(Player visitor){}
    private void getMoneyFromUser(Player visitor){}
    private void vaccinate(Player visitor){}
}
