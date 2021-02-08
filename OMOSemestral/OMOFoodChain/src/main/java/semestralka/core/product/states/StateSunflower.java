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
public class StateSunflower extends ProductState{
    
    public StateSunflower(int price){
        super(price, ProductType.SUNFLOWER);
    }

    @Override
    public ProductState next() {
        return new StateOil(price);
    }
    
    
    
}
