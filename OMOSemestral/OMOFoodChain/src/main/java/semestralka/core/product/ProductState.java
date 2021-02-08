/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.product;

/**
 * Abstract class of product states for product class.
 * State design pattern.
 * 
 * @author luvave
 */
public abstract class ProductState {
    protected ProductType type;
    protected int price;
    
    protected ProductState(int price, ProductType type){
        this.type = type;
        this.price = price > type.getNomalPrice().getAmount() ? price : type.getNomalPrice().getAmount();
    }
    
    public abstract ProductState next();   
    
    public ProductType getType() {
        return this.type;
    }


    public boolean equals(ProductState other) {
        return type == other.getType();
    }

    public int getCurrPrice() {
        return price;
    }

    public void changePrice(int newPrice) {
        if(newPrice > price)
            price = newPrice;
    }

    @Override
    public String toString() {
        return type.toString();
    }
    
    
}
