/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.product;

import semestralka.core.moneysystem.Money;

/**
 * Sample enum "class" that we use for define product.
 * @author luvave
 */
public enum ProductType {
   
    //Making bread
    GRAIN (null, 10, ProductPhase.FARMED),
    FLOUR(GRAIN, 20, ProductPhase.PROCESSED),
    BREAD (FLOUR, 30, ProductPhase.PROCESSED),
    STORED_BREAD(BREAD, 35, ProductPhase.STORED),
    BREAD_TO_GO(STORED_BREAD, 40, ProductPhase.TRANSPORTED),
    PACKED_BREAD(BREAD_TO_GO, 40, ProductPhase.DISTRIBUTED),
    CRUMBS(PACKED_BREAD, 0, ProductPhase.CONSUMED),

    SUNFLOWER (null, 5, ProductPhase.FARMED),
    OIL (SUNFLOWER, 10, ProductPhase.PROCESSED),
    STORED_OIL(OIL, 15, ProductPhase.STORED),
    OIL_TO_GO(STORED_OIL, 20, ProductPhase.TRANSPORTED),
    PACKED_OIL(OIL_TO_GO, 25, ProductPhase.DISTRIBUTED),
    EMPTY_OIL_BOTTLE(PACKED_OIL, 0, ProductPhase.CONSUMED),
    
    //Making chips 
    POTATOES (null, 10, ProductPhase.FARMED),
    FRENCH_FRIES (POTATOES, 30, ProductPhase.PROCESSED),
    STORED_FRIES(FRENCH_FRIES, 35, ProductPhase.STORED),
    FRIES_TO_GO(STORED_FRIES, 40, ProductPhase.TRANSPORTED),
    PACKED_FRIES(FRIES_TO_GO, 45, ProductPhase.DISTRIBUTED),
    BITS(PACKED_FRIES, 0, ProductPhase.CONSUMED),
    
    //Making meat
    PIG (null, 10, ProductPhase.FARMED),
    PORK (PIG, 25, ProductPhase.PROCESSED),
    STEAK(PORK, 40, ProductPhase.PROCESSED),
    STORED_STEAK(STEAK, 45, ProductPhase.STORED),
    STEAK_TO_GO(STORED_STEAK, 50, ProductPhase.TRANSPORTED),
    PACKED_STEAK(STEAK_TO_GO, 55, ProductPhase.DISTRIBUTED),
    EATEN_STEAK(PACKED_STEAK, 0, ProductPhase.CONSUMED);

    private final ProductType madeof;
    private Money price;
    private ProductPhase phase;
    
    private ProductType(ProductType previous, int cost, ProductPhase phase){
        madeof = previous;
        price = new Money(cost);
        this.phase = phase;
    }
    /**
     * 
     * @return a ProductType of product I need to make this type. Null if this is basic Type
     */
    public ProductType getMadeof(){
        return madeof;
    }

    public ProductPhase getPhase() {
        return phase;
    }
    
    /**
     * Get a minimal price of product
     * @return 
     */
    public Money getNomalPrice(){
        return price;
    }
}
