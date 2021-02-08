/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.orders;

import java.util.Objects;
import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Market;
import semestralka.core.moneysystem.Money;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

/**
 *
 * @author luvave
 */
public class Order {
    protected int id;
    protected ProductType product;
    protected Money totalCost;
    private boolean selling;
    
    private AbstractParty contact;

    /**
     * 
     * @param id number of current order
     * @param product type of product to be traded
     * @param totalCost amount of money offered/demanded
     * @param selling means if is already in stock of Party placing offer (true = selling / false = buying)
     * @param contact An Abstract Party, that submitted this order
     */
    public Order(int id, ProductType product, Money totalCost, boolean selling, AbstractParty contact){
        this.id = id;
        this.product = product;
        this.totalCost = totalCost;
        this.contact = contact;
        this.selling = selling;
    }
    
    /**
     * Setting price of order to price of product
     * @param p product which is sold
     */
    public void productPrice(Product p){
        if(p.getType() == getProductType() && !selling){
            totalCost = p.getCurrPrice();
            
        }
        
    }

    public int getId(){ return id; }
    public ProductType getProductType(){ return product; }
    public AbstractParty getSubmitter(){ return contact; }
    public boolean isSelling(){ return selling; }
    public Money getTotalCost(){ return totalCost; }
    
    
    /**
     * 
     * @return a copy of order instance
     */
    public Order copy(){
        return new Order(id, product, totalCost, selling, contact);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.selling != other.selling) {
            return false;
        }
        if (this.product != other.product) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order for "  + product + " from " + contact;
    }
    
    
    
}
