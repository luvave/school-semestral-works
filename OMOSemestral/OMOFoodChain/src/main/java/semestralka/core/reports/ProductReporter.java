/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import semestralka.core.entities.AbstractParty;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public class ProductReporter extends AbstractReporter{
     protected Map<ProductType, Integer> numbers;
     
     public ProductReporter(){
         super();
         numbers = new HashMap();
     }

    public ProductReporter(String filename) {
        super(filename);
        numbers = new HashMap();
    }

    public void report(List<AbstractParty> parties, int dayNum){
        writer.println("--------------------Day " +(dayNum>9?dayNum:"0"+dayNum)+"--------------------");
        for (Iterator<AbstractParty> it = parties.iterator(); it.hasNext();) {
            AbstractParty party = it.next();
            party.accept(this);
        }
    }
    
    public void visit(AbstractParty owner, List<Product> inventory){
        for (Iterator<Product> it = inventory.iterator(); it.hasNext();) {
            Product p = it.next();
            increment(p);
            
            writer.println(p);
            writer.println("Currently held by "+owner+ " for " + p.getDaysHere() + " days");
            writer.println();
            writer.flush();  
        }
        if(numbers.size() > 0){
            writer.println("Total number of Product types:");

            numbers.keySet().forEach((t) -> {
                writer.println(t + ": "+numbers.get(t));
                writer.flush();  
            });
            numbers.clear();
            writer.println();
            writer.flush();   
        }
    }
    
    
    private void increment(Product p){
        int val = 0;
        if(numbers.containsKey(p)) val = numbers.get(val);
        numbers.put(p.getType(), val+1);
        
    }
    
}
