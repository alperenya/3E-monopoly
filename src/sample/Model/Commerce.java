package sample.Model;

public class  Commerce {
    //Properties
    private Player buyer;
    private Player seller;
    private Property offeredProperty;
    private Property requestedProperty;
    private int offeredMoney;
    private int requestedMoney;

    //Constructor
    public Commerce(Player buyer, Player seller, Property offeredProperty, Property requestedProperty, int offeredMoney, int requestedMoney){
        this.buyer = buyer;
        this.seller = seller;
        this.offeredProperty = offeredProperty;
        this.requestedProperty = requestedProperty;
        this.offeredMoney = offeredMoney;
        this.requestedMoney = requestedMoney;
    }
    //Methods
    public boolean exchange(){
        if(offeredProperty != null && buyer.getProperties().contains(offeredProperty) && buyer.getMoney() > offeredMoney){
            if(requestedProperty != null && seller.getProperties().contains(requestedProperty) && seller.getMoney() > requestedMoney){
                buyer.trade(seller, offeredMoney, offeredProperty, requestedMoney, requestedProperty);
            }
            else if(requestedProperty == null && seller.getMoney() > requestedMoney){
                buyer.trade(seller, offeredMoney, offeredProperty, requestedMoney, requestedProperty);
            }
            else
                return false;
        }
        else if(offeredProperty == null && buyer.getMoney() > offeredMoney){
            if(requestedProperty != null && seller.getProperties().contains(requestedProperty) && seller.getMoney() > requestedMoney){
                buyer.trade(seller, offeredMoney, offeredProperty, requestedMoney, requestedProperty);
            }
            else if(requestedProperty == null && seller.getMoney() > requestedMoney){
                buyer.trade(seller, offeredMoney, offeredProperty, requestedMoney, requestedProperty);

            }
            else
                return false;
        }
        else
            return false;
        return true;
    }
}
