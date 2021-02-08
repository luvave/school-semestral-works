/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.control;

import semestralka.core.entities.*;
import semestralka.core.product.ProductType;


/**
 * This class contains some sample scenarios of the food ecosystem.
 * @author luvave
 */

public class TestingScenario1 extends TestingScenarioBase{
    
    public TestingScenario1(){
        super();
        market = worldController.createFullMarket();
        
    }
    
    
    @Override
    public void run(){
        Farmer farmerSunGrain = partyController.createBasicFarmer(market, "Old McDonald", 30);
        farmerSunGrain.addProduct(ProductType.SUNFLOWER);
        farmerSunGrain.addProduct(ProductType.GRAIN);
        
        Farmer farmerPigPot = partyController.createBasicFarmer(market, "AnimalFarm", 20);
        farmerPigPot.addProduct(ProductType.PIG);
        farmerPigPot.addProduct(ProductType.POTATOES);

        Processor processorOilFlour = partyController.createBasicProccessor(market, "Euler", 20);
        processorOilFlour.addProduct(ProductType.OIL);
        processorOilFlour.addProduct(ProductType.FLOUR);
               
        
        Processor processorFrenchBread = partyController.createBasicProccessor(market, "KarlBread inc.", 10);
        processorFrenchBread.addProduct(ProductType.BREAD);
        processorFrenchBread.addProduct(ProductType.FRENCH_FRIES);
        
        
        Processor processorPork = partyController.createBasicProccessor(market, "Butcher of Blaviken", 20);
        processorPork.addProduct(ProductType.PORK);
        processorPork.addProduct(ProductType.STEAK);
        

        Store store = partyController.createFullStore(market, "Storage C:", 15);

        Logistics logistic = partyController.createBasicLogistics(market, "Convoy", 15);
        logistic.addProduct(ProductType.BREAD_TO_GO);
        logistic.addProduct(ProductType.OIL_TO_GO);
        logistic.addProduct(ProductType.FRIES_TO_GO);
        
        Logistics logistic2 = partyController.createBasicLogistics(market, "Getaway car", 15);
        logistic2.addProduct(ProductType.STEAK_TO_GO);

        Distributor distributor = partyController.createBasicDistributor(market, "Peddler", 25);
        distributor.addProduct(ProductType.PACKED_OIL);
        distributor.addProduct(ProductType.PACKED_BREAD);
        
        Distributor allDistributor = partyController.createFullDistributor(market, "My man", day);

        Consumer consumer = partyController.createRichFullConsumer(market, "Old white man");
        

        parties = partyController.getCreatedParties();

        System.out.println("World done");
        
        consumer.buySomething(ProductType.PACKED_BREAD);
               
        
        worldController.runForDays(5);       
        allDistributor.buy(ProductType.STORED_FRIES);
        worldController.runForDays(6);
        store.buy(ProductType.OIL);
        

        worldController.productReport();
        worldController.runForDays(5);
        
        consumer.buySomething(ProductType.PACKED_OIL);
        
        worldController.runForDays(5);
        
        store.buy(ProductType.STEAK);
        consumer.buySomething(ProductType.PACKED_STEAK);
        worldController.partyReport();
        worldController.runForDays(10);
        
        worldController.productAndPartyReport();
    }

}
