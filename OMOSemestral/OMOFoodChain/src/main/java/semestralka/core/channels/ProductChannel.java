/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.channels;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Market;
import semestralka.core.moneysystem.Money;
import semestralka.core.orders.Order;
import semestralka.core.orders.OrderBuilder;
import semestralka.core.product.ProductType;

/**
 * Channel is grouping all parties, that trade product of current type. 
 * Serves as subject for observer design pattern.
 * @author luvave
 */

public class ProductChannel extends BasicChannel{
    private final ProductType type;
    private List<Order> listOfOrders;
    private final Market market;
    
    /**
     * Creating an instance of channel
     * @param market 
     * @param type Product type, that will be traded here
     */
    public ProductChannel(ProductType type, Market market){
        this.type = type;
        this.market = market;
        parties = new LinkedList();
        listOfOrders = new LinkedList();
        
    }
    /**
     * Get ProductType of this channel
     * @return ProductType     */
    public ProductType getType() {
        return this.type;
    }
   
    /**
     * <p>Submitting order for other parties to trade with</p>
     * <p>!! Parameters /Cost/ and /sell/ must be set !!<br>
     * When some attributes miss, order will not be placed</p> 
     * @param builder A builder with predefined parameters of /Cost/ and /sell/
     */
    public Order submitOrder(OrderBuilder builder){
        builder.setType(type);
        builder.setId(market.getNextOrderNumber());
        Order add = null;
        if(builder.isReady()){
            add = builder.buildOrder();
            listOfOrders.add(add);
        }
        return add.copy();
    }
    
    

    /**
     * Get a copy of orders for product traded in channel
     * @return list of orders in placed in channel
     */
    public List<Order> getListOfOrders(){
        return List.copyOf(listOfOrders);
    }
    
    /**
     * Method is checking, whether the order is still ongoing and offered in channel. 
     * If true, this order is assigned to particular Party
     * @param order Order to be assigned
     * @param party that claims the order
     * @return Order if there is an existing order like this in channel, null if not
     */
    public Order getOrder(Order order, AbstractParty party){ //should add HASH
        if(listOfOrders.contains(order)){
            listOfOrders.remove(order);
            party.addOrderToProgress(order);
            return order; 
        }
        return null;   
    }    
}
