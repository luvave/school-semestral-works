/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import semestralka.core.product.ProductType;
import semestralka.exceptions.NotBasicProductException;

/**
 *
 * @author travnja5
 */
public class ProductFactoryTest {
    ProductFactory pf;
    
    public ProductFactoryTest(){
        pf = new ProductFactory();
    }
    
    @Test
    public void testCreateType_notBasicProduct_throwsException() {
        Assertions.assertThrows(NotBasicProductException.class, ()->pf.createType(ProductType.OIL, "name"));                
    }
    
    
    
}
