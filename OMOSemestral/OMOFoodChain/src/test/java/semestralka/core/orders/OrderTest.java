/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.orders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Farmer;
import semestralka.core.entities.Market;
import semestralka.core.entities.ProductFactory;
import semestralka.core.moneysystem.Money;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class OrderTest {
    
    /**
     * Test of copy method, of class Order.
     */
    @Test
    public void testCopy() {
        Order instance = new Order(1, ProductType.BREAD, new Money(3), true, 
                new Farmer("McDonald", 3, Market.getInstance(), new ProductFactory()));
        Order expResult = instance.copy();
        
        List<Order> nef = new LinkedList();
        nef.add(instance);

        assertEquals(true, nef.contains(expResult));

    }


    
}
