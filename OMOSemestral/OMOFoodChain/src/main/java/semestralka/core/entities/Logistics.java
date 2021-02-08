/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import semestralka.core.moneysystem.Money;
import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductPhase;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class Logistics extends AbstractParty{

    List<Order> ordered = new ArrayList();

    public Logistics(String name, int startBalance, Market market, int fee) {
        super(name, startBalance, market, fee);
    }

    public Logistics(String name, int startBalance, Market market, int margin, List<ProductType> ableToDo) {
        super(name, startBalance, market, margin, ableToDo);
        
    }

    @Override
    protected void update() {
        workService();
    }

    @Override
    protected void affectProduct(Product product) {
        product.transported(nameOfCompany);
    }
}
