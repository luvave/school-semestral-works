/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import semestralka.core.product.Product;
import semestralka.core.product.ProductType;
import semestralka.core.product.states.StateGrain;
import semestralka.core.product.states.StatePig;
import semestralka.core.product.states.StatePotatoes;
import semestralka.core.product.states.StateSunflower;
import semestralka.exceptions.NotBasicProductException;

/**
 * Creates basic products. Used by farmer.
 * @author luvave
 */
public class ProductFactory {
    

    public Product createType(ProductType type, String name) throws NotBasicProductException{
        String message = "Created by '" + name+"'";
        switch (type) {
            case GRAIN:
                return new Product(new StateGrain(0), message);
            case PIG:
                return new Product(new StatePig(0), message);
            case SUNFLOWER:
                return new Product(new StateSunflower(0), message);
            case POTATOES:
                return new Product(new StatePotatoes(0), message);
            default:
                throw new NotBasicProductException("Farmer cannot fulfill orders, in which product require prerequisites!!!");
        }
    }
    
}
