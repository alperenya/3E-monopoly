package sample.Model;

//import com.sun.org.apache.xpath.internal.operations.Neg;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Bot extends Player{

    //contructors
    public Bot(String name, Pane piece, String shape, String password, Cell cell){
        super(name, piece, shape, password, cell);
    }
    public Bot(){}
    //methods
    public Boolean decideBuyingProperty(){
        if(position instanceof Property)
            return buyProperty((Property) position);
        return false;
    }

    public Boolean decideMortgagingProperty()
    {
        if (money <= 0)
            return mortgage(properties.get((int)(Math.random() * (properties.size() - 1))));
        return false;
    }

    public Boolean decideBuilding(){
        for (Property p:canBuild())
            return buildHouse((Neighbourhood) p);
        return false;
    }

    public Boolean decideTrading(Property botProperty, Property sellerProperty, int botMoney, int sellerMoney){
        if(canBuild().contains(botProperty))
            return false;
        if(botProperty.getPrice() + botMoney < sellerProperty.getPrice() + sellerMoney)
            return false;
        return true;
    }

}