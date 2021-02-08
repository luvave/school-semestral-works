/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.reports;

import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class History {
        private ProductType type;
        private int daysHere;
    
    /**
     * After selling product to another party, mark product I worked on
     * @param p Product I want to remember
     */
    public History(Product p) {
        this.type = p.getType();
        this.daysHere = p.getDaysHere();
    }

    @Override
    public String toString() {
        return "Product " + type + " was on the floor for "+daysHere + " day(s)."; 
    }
    
        
}
