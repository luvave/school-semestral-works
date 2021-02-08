/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import java.util.ArrayList;
import java.util.List;
import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductPhase;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class Processor extends AbstractParty{    
    List<Order> ordered = new ArrayList();

    public Processor(String name, int startBalance, Market market) {
        super(name, startBalance, market, 0);
    }

    public Processor(String name, int startBalance, Market market, int margin) {
        super(name, startBalance, market, margin);
    }

    public Processor(String name, int startBalance, Market market, List<ProductType> ableToDo) {
        super(name, startBalance, market, 0, ableToDo);
        for(ProductType p : ableToDo){
            if(p.getPhase() != ProductPhase.PROCESSED) continue;
            if(p.getMadeof() == null) continue;
            if(!ableToDo.contains(p.getMadeof())) needForWork.add(p.getMadeof());
        }
    }

    public Processor(String name, int startBalance, Market market, int margin, List<ProductType> ableToDo) {
        super(name, startBalance, market, margin, ableToDo);
        for(ProductType p : ableToDo){
            if(p.getPhase() != ProductPhase.PROCESSED) continue;
            if(p.getMadeof() == null) continue;
            if(!ableToDo.contains(p.getMadeof())) needForWork.add(p.getMadeof());
        }
    }

    @Override
    protected void update() {
        workService();
    }

    @Override
    protected void affectProduct(Product product) {
        product.processed(nameOfCompany);
    }

    @Override
    public void removeProduct(ProductType type) {
        super.removeProduct(type); 
        
        
    }
  
}
