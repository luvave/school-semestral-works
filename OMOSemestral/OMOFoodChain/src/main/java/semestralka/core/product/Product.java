/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.product;

import java.util.Objects;
import semestralka.core.moneysystem.Money;
import semestralka.core.moneysystem.Transaction;

/**
 * Main Product class. Context for state design pattern.
 * @author luvave
 */


public class Product {    
    private ProductState state;
    private String wentThrough;
    private int daysAtOnePlace;
    
    /**
     * Create new product
     * @param baseState State in which product is. In simulation only states with no previous should be created automatically
     * @param createdBy name of company, that created this product
     */
    public Product(ProductState baseState, String createdBy) {
        this.state = baseState;
        wentThrough = createdBy+ "\n";
        daysAtOnePlace = 0;
    }
    
    public void nextDayInPartyStock(){
        daysAtOnePlace++;
    }
    
    
    public void stored(String nameOfCompany){
        daysAtOnePlace++;
        ProductState next = state.next();
        if(!next.equals(state)){
            wentThrough += "In '" + nameOfCompany + "' was product "+ state+ " succesfully stored long enough to become " +next  +"\n";
            state = state.next();
        }
    }

    public void consumed(String nameOfCompany){
        wentThrough += "Product was consumed by '" + nameOfCompany + "'\n";
        daysAtOnePlace++;
        ProductState next = state.next();
        if(!next.equals(state)){
            wentThrough += "'"+nameOfCompany + "' consumed " + state + ". Only "+next + " left\n";
            state = state.next();      
        }
    }

    public void transported(String nameOfCompany){
        daysAtOnePlace++;
        ProductState next = state.next();
        if(!next.equals(state)){
            wentThrough += "'"+nameOfCompany + "' is transporting product " + state + " as "+next + "\n";
            state = state.next();      
        }
    }

    public void distributed(String nameOfCompany){
        daysAtOnePlace++;
        ProductState next = state.next();
        if(!next.equals(state)){
            wentThrough += "By '" + nameOfCompany + "' was product "+ state+" succesfully distributed as " +next + "\n";
            state = state.next();
        }
    }
    

    public void processed(String nameOfCompany){
        daysAtOnePlace++;
        ProductState next = state.next();
        System.out.println("Producing "+state);
        if(!next.equals(state)){
            System.out.println("into "+next);
            wentThrough += "In '" + nameOfCompany + "' was product succesfully processed from " + state + " to "+next + "\n";
            state = state.next();
        }
    }
    
    public void sold(Transaction transaction){
        wentThrough += "Product was in stock of '" +transaction.getSeller()+ "' for " + daysAtOnePlace+  " days\n";
        daysAtOnePlace = 0;
        wentThrough += "'"+transaction.getSeller() + "' sold this product to '" + transaction.getBuyer()+ "' for " +transaction.getCost() + "\n";
    }


    public Money getCurrPrice(){
        return new Money(state.getCurrPrice());
    }
    
    public void applyMargin(double percentage){
        state.changePrice((int)Math.ceil(state.getCurrPrice()*(1+percentage)));
        
    }

    
    public ProductType getType(){
        return state.getType();
    }

    @Override
    public String toString() {
        return state.toString()+ "\n" + wentThrough;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
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
        final Product other = (Product) obj;
        return Objects.equals(this.getType(), other.getType());
    }
    
    
    public int getDaysHere(){
        return daysAtOnePlace;
    }
     
}
