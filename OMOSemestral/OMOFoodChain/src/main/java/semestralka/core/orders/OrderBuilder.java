/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.orders;

import semestralka.core.entities.AbstractParty;
import semestralka.core.moneysystem.Money;
import semestralka.core.product.ProductType;

/**
 *
 * @author luvave
 */

public class OrderBuilder {
    private int ID;
    private ProductType type;
    private Money cost;
    private boolean sell;
    AbstractParty contact;
    
    private boolean IDset, typeSet, costSet, sellSet, contactSet;    
    
    public OrderBuilder(){
        reset();
    }
    
    /**
     * Clearing all values after completing order
     */
    public final void reset(){
        ID = 0;
        cost = new Money(0);
        type = null;        
        contact = null;
        sell = IDset = typeSet = costSet = sellSet = contactSet = false;
    }
    
    /**
     * setting ID of the order
     * @param ID 
     */
    public void setId(int ID){
        this.ID  = ID;
        IDset = true;
    }
    /**
     * Setting cost of the order
     * @param moneyMoney 
     */
    public void setMoney(Money moneyMoney){
        cost = moneyMoney;
        costSet = true;
    }
    /**
     * Setting type of product I am trading
     * @param type 
     */
    public void setType(ProductType type){
        this.type = type;
        typeSet = true;
    }
    /**
     * Setting a submitter of order
     * @param contact 
     */
    public void setContact(AbstractParty contact){
        this.contact = contact;
        contactSet = true;
    }
    
    public void setSelling(boolean amISelling){
        sellSet = true;
        sell = amISelling;               
    }
    
    /**
     * Checking if all values were set correctly
     * @return true if all vlues were set and order can be build
     */
    public boolean isReady(){
        return IDset && costSet && sellSet && contactSet && typeSet;
    }
    
    /**
     * Creates order from given parameters<br>
     * Undefined behavior if some parameters were not set - check  {@link #isReady()}
     * @return created order
     */
    public Order buildOrder(){
        Order ret = new Order(ID, type, cost, sell, contact);
        reset();
        return ret;
    }
    
    
    
    
}
