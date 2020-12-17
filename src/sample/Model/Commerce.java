package sample.Model;

public class  Commerce {
    //Properties
    protected static Player buyer;
    protected static Player seller;
    protected static Property buyerProperty;
    protected static Property sellerProperty;

    //Constructor
    public Commerce(Player buyer, Player seller, Property buyerProperty, Property sellerProperty){
        this.buyer = buyer;
        this.seller = seller;
        this.buyerProperty = buyerProperty;
        this.sellerProperty = sellerProperty;
    }
    //Methods
    public void exchange(){
        if(seller.removeProperty(sellerProperty)){
            buyer.addProperty(sellerProperty);
        }

        if(buyer.removeProperty(buyerProperty)){
            seller.addProperty(buyerProperty);
        }
    }
}
