/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.product.states;

import semestralka.core.product.ProductState;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class StatePig extends ProductState{

    public StatePig(int price) {
        super(price, ProductType.PIG);
    }
    
    @Override
    public ProductState next() {
        return new StatePork(price);
    }  
    
    
}
