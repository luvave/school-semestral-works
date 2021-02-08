/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class Consumer extends AbstractParty {
    List<Order> ordered = new ArrayList();
    int maxLimit = 10;
    
    /**
     * Create new instance of Consumer
     * @param name company name
     * @param startBalance Money balance
     * @param market market reference
     */
    public Consumer(String name, int startBalance, Market market) {
        super(name, startBalance, market, 0);
    }
    
    /**
     * New instance of consumer with unlimited money sources
     * @param name company name
     * @param market market reference
     */
    public Consumer(String name, Market market) {
        super(name, Integer.MAX_VALUE, market, 0);;
    }
    
    /**
     * New instance of consumer with unlimited money sources predefined productTypes 
     * @param name company name
     * @param market market reference
     * @param ableToEat List of ProductType company is able to consume
     */
    public Consumer(String name, Market market, List<ProductType> ableToEat){
        super(name, Integer.MAX_VALUE, market, 0, ableToEat);
    }

    public void buySomething(ProductType product){
        orderProduct(product);
    }

    @Override
    protected void update() {
        ordered.forEach((o) -> {
            ProductType needed = o.getProductType().getMadeof();
            Product productInStock = inStock(needed);
            if (productInStock != null) {
                affectProduct(productInStock);
                realizeTransaction(o);
            }
        });
        if(inventory.size() > maxLimit) inventory.clear();
    }

    @Override
    protected void affectProduct(Product product) {
        product.consumed(nameOfCompany);
    }
}
