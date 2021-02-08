package semestralka.control;

import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Consumer;
import semestralka.core.entities.Market;
import semestralka.core.product.ProductType;
import semestralka.core.reports.TransactionReporter;

import java.util.List;

/**
 * Controller that use to buy actions in simulation
 * @author luvave
 */
public class ActionController {
    private int dayNum;
    
    public ActionController(){
        dayNum = 0;
    }
    
    public void DayCycle(List<AbstractParty> parties, int days, TransactionReporter transactionReporter, Market market){
        for(int i = 0; i<days; i++ ){
            for(AbstractParty party : parties){                
                party.work();
            }
            if(transactionReporter!=null){
                market.getBlockchainChannel().accept(transactionReporter);
            }
            dayNum++;
            System.out.println("--------------------End of day " +(dayNum>9?dayNum:"0"+dayNum)+"--------------------");
            System.out.println(""); 
        }
    }

    public void makeOrder(ProductType productType, List<AbstractParty> parties){
        for(AbstractParty a : parties){
            if(a.getClass() == Consumer.class){
               ((Consumer) a).buySomething(productType);
            }
        }
    }
    
    public int getDayNumber(){
        return dayNum;
    }

}
