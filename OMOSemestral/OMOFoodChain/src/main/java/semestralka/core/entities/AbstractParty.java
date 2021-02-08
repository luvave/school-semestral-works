/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.naming.directory.InvalidAttributeValueException;

import semestralka.core.channels.BlockchainChannel;
import semestralka.core.channels.ProductChannel;
import semestralka.core.moneysystem.Money;
import semestralka.core.moneysystem.Transaction;
import semestralka.core.orders.Order;
import semestralka.core.orders.OrderBuilder;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;
import semestralka.core.reports.History;
import semestralka.core.reports.PartyReporter;
import semestralka.core.reports.ProductReporter;


/**
 * This abstract class is used for making new parties/entities/companies
 * @author luvave
 */
public abstract class AbstractParty{   
    protected final String nameOfCompany;
    protected final Market market; //Is set to private and must be final because all parties will use the same market
    
    protected final Money balance;
    protected OrderBuilder builder;
    protected int margin;

    protected LinkedList<Transaction> blockchain;
    protected BlockchainChannel blockchainChannel;
    protected Transaction transactionInProgress;

    protected List<Order> inProgress, myOrders;
    protected List<ProductChannel> tradingWith; //Currently used channels, for buy or sell
    protected List<Product> inventory;  //Current items on local currentlyStoredIn
    protected List<ProductType> ableToDo, needForWork;
    protected List<History> productsPassed;
    protected List<Order> ordered = new ArrayList();
    
    public AbstractParty(String name, int startBalance, Market market, int margin){
        this.market = market;
        this.nameOfCompany = name;
        this.balance = new Money(startBalance);
        
        if(margin < 0 || margin > 100){
            margin = 10;
            System.err.println("Margin does not fit in range 0 - 100, setting it to 10");
        }
        this.margin = margin;
        
        inventory = new ArrayList();
        tradingWith = new ArrayList();
        
        myOrders = new LinkedList();
        inProgress = new ArrayList();
        builder = new OrderBuilder();
        
        needForWork  = new ArrayList();
        
        blockchainChannel = market.getBlockchainChannel();        
        blockchain = blockchainChannel.getBlockchain();
        transactionInProgress = null;
        blockchainChannel.addParty(this);
        productsPassed = new LinkedList();
        ableToDo = new ArrayList();
    }
    
    public AbstractParty(String name, int startBalance, Market market, int margin, List<ProductType> ableToDo){
        this(name, startBalance, market, margin);
        for(ProductType pt : ableToDo){
            addProduct(pt);
        }

    }
    
    public String getName(){
        return nameOfCompany;
    }
    
    public List<ProductType> getWhatPartyCanMake(){
        return ableToDo;
    }
    
/* -------------------- ESSENTIAL EVERYDAY METHODS -------------------- */
    /**
     * Template method called each day of simulation
 Represents turn of particular party
 
 1st updates all products and work on current orders
 2nd checks for orders in channels - accepts and currentlyStoredIn them or ignore them
     */
    public final void work(){
        update();
        checkNewOrders();
        storingProducts();
    }  
    
    /**
     * updates all products currently on stock
     */
    protected abstract void update();

    /**
     * should add specific message to a String of Product
     * calls function inside product, meant to your kind of work.. it adds the String and change product to next state
     * @product A product, which's String should be updated
     */
    protected abstract void affectProduct(Product product);

    /**
     * Checks orders in channel, where does participate and according to price
     * decides whether is worth it, then accepts or ignores offer
     * Accepting offer will remove it from a channel
     * 
     */
    protected void checkNewOrders(){
        //check if there is are any orders in channels I am able and willing to complete
        //on true add them in inProgress
        tradingWith.forEach((c) -> {
            c.getListOfOrders().forEach((d) -> {
                if(ableToDo.contains(d.getProductType()) && !d.isSelling()){
                    c.getOrder(d, this);
                }
            });


//            what the hell is this??
//            c.getListOfOrders().stream().filter((o) -> (ableToDo.contains(o.getProductType()))).forEachOrdered((o)-> {
//                c.getOrder(o, this);
//            });
        });
    }
    
    protected void storingProducts(){
        System.out.println(nameOfCompany);
        for(ProductType pt: needForWork){
            System.out.print(pt + " ");
        }
                System.out.println("");
        for(Product p : inventory){
            if(needForWork.contains(p.getType()))
                affectProduct(p);
            p.nextDayInPartyStock();
        }
    }
            
    protected void workService(){
        List<Order> finished = new LinkedList();
        ordered.forEach((o) -> {
            Product productInStock = inStock(o.getProductType());
            if (productInStock != null) {
                realizeTransaction(o);
                finished.add(o);
            }
        });
        ordered.removeAll(finished);

        for(Order o : inProgress){
            Product productInStock = inStock(o.getProductType());
            if(productInStock != null){
                realizeTransaction(o);
                continue;
            }
            ProductType needed = o.getProductType().getMadeof();
            if(ableToDo.contains(needed)){
                needed = needed.getMadeof();
                productInStock = inStock(needed);
                if(productInStock != null){
                    //if I have phase before phase I need
                    continue;
                }
            }
            orderProduct(needed);
            ordered.add(o);
        }
        inProgress.clear();
    }

    //get order from channel... maybe fix later???
    public void addOrderToProgress(Order order){
        this.inProgress.add(order);
    }

    /**
     * Party will legaly obtain a Product he may use to create sth
     * @param type ProductType of product I want party to 
     */
    public void buy(ProductType type){
        if(needForWork.contains(type)){
            if(needForWork.contains(type.getMadeof()))
                type = type.getMadeof();
            orderProduct(type);
            return;
        }
        if(ableToDo.contains(type)){
            buy(type.getMadeof());
            return;
        }
        System.out.println("Unable to do this :((\nI can buy only these: ");
        for(ProductType p:needForWork){
            System.out.print(p + ", ");
        }
        System.out.println("");    
    }
    
    protected Product inStock(ProductType type){
        for (Product p : inventory) {
            if(p.getType() == type) {
                return p;
            }
        }
        return null;
    }
    
    
/* -------------------- TRANSACTIONS AND TRADING -------------------- */
   
    public boolean checkTransaction(Transaction trans){
        String prevhash;
        if(blockchain.isEmpty()){
            prevhash = "0";
        }
        else{
            prevhash = blockchain.getLast().getHash();
        }
        if(trans.getPrevHash().equals(prevhash)){
            return true;
        }
        return false;
    }

    public void addTransactionToBlockchain(Transaction transaction){
        this.blockchain.add(transaction);
    }
    
    /**
     * 
     * @param order
     * @return true if the transaction was approved
     */
    protected final boolean realizeTransaction(Order order){
        System.out.println(this.nameOfCompany + " I got order: " + order);
        transactionInProgress = order.getSubmitter().acceptTransaction(order, this);
                
        if(transactionInProgress == null){ //transaction was not accepted
            System.out.println(nameOfCompany + ": Yo, I got null transaction.. wtf");
            if(!order.isSelling()){
                Product p = inStock(order.getProductType());
                if(p!=null) offerProduct(p);
            }
            return false;
        }
        
        if(order.isSelling()){ //submitter of the order is not selling -> I must be seller
            transactionInProgress.setOtherAccount(balance);
            if(inStock(order.getProductType())== null){ //checking whether I have the product in stock
                return false;
            }
        }
        else transactionInProgress.setOtherAccount(balance);
        
        if(blockchain.isEmpty()) transactionInProgress.setPrevHash("0");
        else transactionInProgress.setPrevHash(blockchain.getLast().getHash());
        
        //System.out.println(transactionInProgress.getPrevHash());
        //System.out.println(transactionInProgress.getHash());
        if(this.blockchainChannel.addNewTransaction(transactionInProgress)){
            transactionInProgress.realize();
            if(order.isSelling()){
                inventory.add(order.getSubmitter().requestProduct(transactionInProgress, order));
            }
            else{
                Product product = inStock(order.getProductType());
                
//                System.out.println("product is " + product);
//                for(Product p: inventory){
//                    System.out.println(p.getType());
//                }
//                System.out.println("For work I need");
//                for(ProductType nfw: needForWork){
//                    System.out.println(nfw);
//                }
                
                inventory.remove(product);
                productsPassed.add(new History(product));
                product.sold(transactionInProgress);
                order.getSubmitter().acceptProduct(product, order);
                
            }
            System.out.println("Transaction for "+order+" done");
            return true;
        }
        else{
            System.err.println("Blockchain failed");
            return false;
        }

    }

    /**
     * Used 
     * @param order
     * @param other
     * @return null if Transaction wasn't submitted by this party, new transaction otherwise
     */
    protected final Transaction acceptTransaction(Order order, AbstractParty other){
        if(!myOrders.contains(order)){
            return null;
        }
        transactionInProgress = null;
        if(order.isSelling()){
            Product p = inStock(order.getProductType());
            if(p != null) order.productPrice(p);
            else {
                return null;
            }
        }
        if(inStock(order.getProductType()) == null){
            transactionInProgress = new Transaction(order, other, balance);
        }
        return transactionInProgress;
    }
    
     /**
     * After succesfull transaction will seller party use this method to pass product to buyer
     * @param product Subject of transaction. Just bought product
     * @param o Order on which the exchange is based
     */
    public void acceptProduct(Product product, Order o){
        myOrders.remove(o);
        inventory.add(product);
        transactionInProgress = null;
    }
    
    /**
     * After buying a product a buyer party will request passing product via this method
     * @param t transaction proving payment has been done
     * @param o Order on which the exchange is based
     * @return 
     */
    public Product requestProduct(Transaction t, Order o){
        myOrders.remove(o);
        if(transactionInProgress != null && t.isAccepted() && t.equals(transactionInProgress)){
            Product p = inStock(t.getType());
            if(p != null){
                inventory.remove(p);
                productsPassed.add(new History(p));
                p.sold(t);
            }
            transactionInProgress = null;
            return p;
        }
        return null;
    }
    
    /**
     * removing order from expected deliveries
     * @param order 
     */
    public final void removeOrder(Order order){
        myOrders.remove(order);

    }
    
    /* -------------------- PLACING NEW ORDERS -------------------- */
    
    protected void orderProduct(ProductType type){
        //search existing offers for the cheapest
        System.out.println(this.nameOfCompany + " Ordering " + type);

        Order best = null;
        Money min = null;
        for(ProductChannel c : tradingWith){
            if(c.getType() == type){
                for(Order o : c.getListOfOrders()){
                    if(o.getProductType() == type && o.isSelling()){
                        
                        if(min == null || !o.getTotalCost().ableToPay(min)){
                            best = o;
                            min = o.getTotalCost();
                        }
                    }
                }
                if(best != null){
                    realizeTransaction(best);
                    return;
                }
            }
        }
        System.out.println(this.nameOfCompany + " created order for " + type);
        
        Money price = type.getNomalPrice(); //somehow get a price
        builder.setContact(this);
        builder.setMoney(price);
        builder.setSelling(false);
        for(ProductChannel c : tradingWith){
            if(c.getType() == type){
                myOrders.add(c.submitOrder(builder));
                return;
            }
        }
        joinChannel(type);
        for(ProductChannel c : tradingWith){
            if(c.getType() == type){
                myOrders.add(c.submitOrder(builder));
                return;
            }
        }
    }
    
    
    protected void offerProduct(Product product){
        if(!ableToDo.contains(product.getType())){
            System.err.println("Party is not able to sell products of type "+product.getType());
            return;
        }
        product.applyMargin((double)(margin/100));
        Money price = product.getCurrPrice();
        builder.setContact(this);
        builder.setMoney(price);
        builder.setSelling(true);
        for(ProductChannel c : tradingWith){
            if(c.getType() == product.getType()){
                myOrders.add(c.submitOrder(builder));
                return;
            }
        }
        System.err.println("Party is not a member of channel for selling "+product.getType());
    }

  
/* -------------------------- REPORTS -------------------------- */
    public void accept(ProductReporter visitor){
        visitor.visit(this, inventory);
    }
    
    public void accept(PartyReporter visitor){
        visitor.visit(this, balance, inventory.size(), myOrders.size(), inProgress.size(), productsPassed);
    }
    
    
    
/* -------------------- JOINING AND LEAVING CHANNELS -------------------- */
    
    /**
     * Adding a type of product company is able to sell
     * joins particular channel trading this type of product
     * @param type type of new product company will be selling
     */
    public void addProduct(ProductType type) {
        if(type.getMadeof() == null){
            System.err.println("Cannot create basic products like "+type);
            return;
        }
        if(!ableToDo.contains(type)){
            ableToDo.add(type);
            joinChannel(type);
            type = type.getMadeof();
            
            if(!ableToDo.contains(type)) joinChannel(type);
            needForWork.add(type);
        }
        
        
    }
    
    
    /**
     * Removing a type of product company is no longer able to buy
 also leaves channel trading this type of product
     * @param type Product type to be removed
     */
    public void removeProduct(ProductType type){
        if(ableToDo.contains(type)){
            ableToDo.remove(type);
            if(!ableToDo.contains(type.getMadeof())){
                needForWork.remove(type);
            }
            leaveChannel(type);
        }
    }
    
    protected void leaveChannel(ProductType type){
        tradingWith.stream().filter((ch) -> 
                (ch.getType() == type)).forEachOrdered((ch) -> {
            ch.removeParty(this);
        });
    }
    
    protected void joinChannel(ProductType type){
        ProductChannel ch = market.getChannel(type);
        ch.addParty(this);
        this.tradingWith.add(ch);

    }
   
    protected final void addToChannels(){
        this.ableToDo.stream().map((type) -> this.market.getChannel(type)).map((channel) -> {
            channel.addParty(this);
            return channel;
        }).forEachOrdered((channel) -> {
            this.tradingWith.add(channel);
        });
    }   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.nameOfCompany);
        hash = 89 * hash + Objects.hashCode(this.market);
        hash = 89 * hash + Objects.hashCode(this.builder);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractParty other = (AbstractParty) obj;
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return nameOfCompany;
    }
    
    
    
    
    
    
}
