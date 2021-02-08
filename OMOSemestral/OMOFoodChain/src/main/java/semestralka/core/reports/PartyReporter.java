/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.reports;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import semestralka.core.entities.AbstractParty;
import semestralka.core.moneysystem.Money;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class PartyReporter extends AbstractReporter{

    public PartyReporter() {
        super();
    }

    public PartyReporter(String filename) {
        super(filename);
    }

    public void report(List<AbstractParty> parties, int dayNum) {
        writer.println("--------------------Day " +(dayNum>9?dayNum:"0"+dayNum)+"--------------------");
        for(AbstractParty ap : parties){
            ap.accept(this);
        }
    }

    public void visit(AbstractParty party, Money balance, int inventorySize, int ordered, int inProgress, List<History> exProducts) {
            writer.println(party.getName());
            writer.println("Able to produce: ");
            for(ProductType pt : party.getWhatPartyCanMake()){
                writer.print(pt+", ");
            }
            writer.println();
            writer.println("currently holding "+balance);
            writer.println(inventorySize +" products in stock");
            writer.println(ordered + " orders awaited");   
            writer.println(inProgress + " orders in progress");
            writer.println("Processed following:");
            for(History h : exProducts) writer.println(h);
            writer.println();
            writer.flush();
    }
    
    
    
}
