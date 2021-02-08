/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;
import semestralka.core.moneysystem.Money;
import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;
import semestralka.exceptions.NotBasicProductException;

import java.util.List;

/**
 * Creates product with basic product states: StateGrain...
 * Accept order. Sell the product. Place order to channel.
 * @author luvave
 */
public class Farmer extends AbstractParty{
    private final ProductFactory productFactory;
    
    public Farmer(String name, int startBalance, Market market, ProductFactory productFactory) {
        super(name, startBalance, market, 0);
        this.productFactory = productFactory;
    }

    public Farmer(String name, int startBalance, Market market, int margin, ProductFactory productFactory) {
        super(name, startBalance, market, margin);
        this.productFactory = productFactory;
    }

    public Farmer(String name, int startBalance, Market market, ProductFactory productFactory, List<ProductType> ableToDo) {
        super(name, startBalance, market, 0, ableToDo);
        this.productFactory = productFactory;
    }

    public Farmer(String name, int startBalance, Market market, int margin, List<ProductType> ableToDo, ProductFactory productFactory) {
        super(name, startBalance, market, margin, ableToDo);
        this.productFactory = productFactory;
    }

    @Override
    protected void update(){        
        createBasicProducts(); //creates products farmer has orders for
        for(Order a : inProgress){
            realizeTransaction(a);
        } 
        inProgress.clear();
    }

    @Override
    protected void affectProduct(Product product) {
        //Is done in creation of product
    }

    @Override
    protected void orderProduct(ProductType type) {       
        if(ableToDo.contains(type)){
            
            try{
                inventory.add(productFactory.createType(type, nameOfCompany));
            }
            catch(NotBasicProductException e){
                System.err.println("ProductFactory is suited for creating only the products, that need no prerequisites,"
                    + "Product "+ type + " is not basic. ProductFactory is therefore not able to create it");
            }
            return;
        }
        System.err.println("Cannot do sir, I am not able to make this product.");
    }
    
    
    
    
    private void createBasicProducts(){
        for(Order a : inProgress){
            try{
                inventory.add(productFactory.createType(a.getProductType(), nameOfCompany));
            }
            catch(NotBasicProductException e){
                System.err.println("ProductFactory is suited for creating only the products, that need no prerequisites,"
                    + "Product "+ a.getProductType()+ " is not basic. ProductFactory is therefore not able to create it");
            }
        }
    }    

    @Override
    public void addProduct(ProductType type) {
        if(type.getMadeof() == null){
            ableToDo.add(type);
            joinChannel(type);
        }
        else{
            System.err.println("I CANNOT MAKE THAT, EVER!");
        }
        
    }

    @Override
    public void buy(ProductType type) {
        if(!ableToDo.contains(type)) return;
        try{
            inventory.add(productFactory.createType(type, nameOfCompany));
        }
        catch(NotBasicProductException e){
                System.err.println("ProductFactory is suited for creating only the products, that need no prerequisites,"
                    + "Product "+ type+ " is not basic. ProductFactory is therefore not able to create it");
        }
    }
    
    
    
    
    
}