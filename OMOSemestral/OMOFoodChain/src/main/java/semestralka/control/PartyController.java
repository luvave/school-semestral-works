package semestralka.control;


import semestralka.core.channels.ProductChannel;
import semestralka.core.entities.*;
import semestralka.core.product.ProductPhase;
import semestralka.core.product.ProductType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller to creates parties to the simulation
 * @author luvave
 */
//This class contains a lot of functions for API usage
public class PartyController { 
    private List<AbstractParty> parties;
    
    public PartyController(){
        parties = new LinkedList();
    }
    
    /**
     * Generate farmer that can do all basic products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Farmer createFullFarmer(Market market, String name, int margin){
        List<ProductType> ableToDo = new ArrayList();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.FARMED){
                ableToDo.add(t);
            }
        }
        ProductFactory farmerHouse = new ProductFactory();
        Farmer farmer = new Farmer(name, 100,market,margin,ableToDo,farmerHouse);
        parties.add(farmer);
        return farmer;
    }

    /**
     * Generate proccessor that can process all basic products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Processor createFullProccessor(Market market, String name, int margin){
        List<ProductType> ableToDo = new ArrayList<>();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.PROCESSED){
                ableToDo.add(t);
            }
        }
        Processor processor = new Processor(name, 100,market, margin, ableToDo);
        parties.add(processor);
        return processor;
    }

    /**
     * Generate proccessor that can process all basic products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Store createFullStore(Market market, String name, int margin){
        List<ProductType> ableToDo = new ArrayList<>();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.STORED){
                ableToDo.add(t);
            }
        }
        Store store = new Store(name, 100,market, margin, ableToDo);
        parties.add(store);
        return store;
    }

    /**
     * Generate logistics that can transfer all basic products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Logistics createFullLogistics(Market market, String name, int margin){
        List<ProductType> ableToDo = new ArrayList<>();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.TRANSPORTED){
                ableToDo.add(t);
            }
        }
        Logistics logistics = new Logistics(name, 100,market, margin, ableToDo);
        parties.add(logistics);
        return logistics;
    }

    /**
     * Generate distributor that can distribute(and pack) all basic products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Distributor createFullDistributor(Market market, String name, int margin){
        List<ProductType> ableToDo = new ArrayList<>();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.DISTRIBUTED){
                ableToDo.add(t);
            }
        }
        Distributor distributor = new Distributor(name, 100,market, margin, ableToDo);
        parties.add(distributor);
        return distributor;
    }

    /**
     * Generate consumer that can buy all products
     * @param market to what market it belongs?
     * @param name name of the company
     * @param margin margin that this company takes from orders
     */
    public Consumer createRichConsumer(Market market, String name){
        Consumer consumer = new Consumer(name, market);
        parties.add(consumer);
        return consumer;
    }
    
    Consumer createRichFullConsumer(Market market, String name) {
        List<ProductType> ableToDo = new ArrayList<>();
        for(ProductType t : ProductType.values()){
            if(t.getPhase()== ProductPhase.CONSUMED){
                ableToDo.add(t);
            }
        }
        Consumer consumer = new Consumer(name, market, ableToDo);
        parties.add(consumer);        
        return consumer;
    }

    public Farmer createBasicFarmer(Market market, String name, int margin){
        ProductFactory farmerHouse = new ProductFactory();
        Farmer farmer = new Farmer(name, 100,market,margin,farmerHouse);
        parties.add(farmer);
        return farmer;
    }

    public Processor createBasicProccessor(Market market, String name, int margin){
        Processor processor = new Processor(name, 100,market, margin);
        parties.add(processor);
        return processor;
    }

    public Store createBasicStore(Market market, String name, int margin){
        Store store = new Store(name, 100,market, margin);
        return store;
    }

    public Logistics createBasicLogistics(Market market, String name, int margin){
        Logistics logistics = new Logistics(name, 100,market, margin);
        parties.add(logistics);
        return logistics;
    }

    public Distributor createBasicDistributor(Market market, String name, int margin){
        Distributor distributor = new Distributor(name, 100,market, margin);
        parties.add(distributor);
        return distributor;
    }

    public Consumer createBasicConsumer(Market market, int startBalance, String name){
        Consumer consumer = new Consumer(name, startBalance, market);
        parties.add(consumer);
        return consumer;
    }
    
    public List<AbstractParty> getCreatedParties(){
        return parties;
    }
    
}
