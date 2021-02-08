/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.control;

import java.util.LinkedList;
import java.util.List;
import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Market;
import semestralka.core.reports.PartyReporter;
import semestralka.core.reports.ProductReporter;
import semestralka.core.reports.TransactionReporter;

/**
 *
 * @author travnja5
 */
public abstract class TestingScenarioBase {
    List<AbstractParty> parties;
    TransactionReporter transReporter;
    PartyReporter partyReporter;
    ProductReporter productReporter;
    
    WorldController worldController;
    PartyController partyController;
    ActionController actionController;
    Market market;
    int day;
    
    public TestingScenarioBase(){
        market = Market.getInstance();
        parties = new LinkedList();
        day = 0;
        
        transReporter = new TransactionReporter("transactions.txt");
        partyReporter = new PartyReporter("parties.txt");
        productReporter = new ProductReporter("products.txt");
        
        partyController = new PartyController();
        worldController = new WorldController(partyController);        
        actionController = new ActionController();
    }
    
    public abstract void run();
        
}
