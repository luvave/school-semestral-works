/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductPhase;
import semestralka.core.product.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author travnja5
 */
public class Distributor extends AbstractParty {
    List<Order> ordered = new ArrayList();

    public Distributor(String name, int startBalance, Market market, int margin) {
        super(name, startBalance, market, margin);
    }

    public Distributor(String name, int startBalance, Market market, int margin, List<ProductType> ableToDo) {
        super(name, startBalance, market, margin, ableToDo);
        for(ProductType p : ableToDo){
            if(p.getPhase() != ProductPhase.DISTRIBUTED) continue;
            if(p.getMadeof() == null) continue;
        }
    }

    @Override
    protected void update() {
        workService();
    }

    @Override
    protected void affectProduct(Product product) {
        product.distributed(nameOfCompany);
    }
}
