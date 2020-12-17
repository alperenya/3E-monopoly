public class  Commerce {
    //Properties
    protected static Player buyer;
    protected static Player seller;
    protected static Property buyerProperty;
    protected static Property sellerProperty;


    //Methods
    public static void exchange(){
        if(seller.removeProperty(sellerProperty)){
            buyer.addProperty(sellerProperty);
        }

        if(buyer.removeProperty(buyerProperty)){
            seller.addProperty(buyerProperty);
        }
    }
}
