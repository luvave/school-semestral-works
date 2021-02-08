/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.entities;

import java.util.HashMap;
import java.util.Map;

import semestralka.core.channels.BlockchainChannel;
import semestralka.core.channels.ProductChannel;
import semestralka.core.moneysystem.Money;
import semestralka.core.product.ProductType;

/**
 * <p>A class representing a environment of capitalism
 * Holds map of channels and standard order prices for each ProductType </p>
 * 
 * <p>Singleton, only one Market exists in environment </p>
 * @author luvave
 */
public class Market {
    private static Market instance = null;
    
    private Map<ProductType, ProductChannel> channelMap;
    private int orderNum;
    private BlockchainChannel blockchain;

    private Market() {
        this.channelMap = new HashMap<>();
        this.orderNum = 0;
        this.blockchain = new BlockchainChannel();
    }
    public static Market getInstance(){
        if(instance == null){
            instance = new Market();
        }
        return instance;
    }
    
    private Market(HashMap<ProductType, ProductChannel> channelMap) {
        this.channelMap = channelMap;
        
    }
    public static Market getInstance(HashMap<ProductType, ProductChannel> channelMap, Map<ProductType, Integer> prices){
        if(instance == null){
            instance = new Market(channelMap);
        }
        return instance;
    }
    
    /**
     * Method used to detect, whether a channel for trading current type exists
     * @param type type of Product
     * @return true if channel was already created and put in Market
     */
    public boolean existsChannel(ProductType type){
        return this.channelMap.containsKey(type);
    }
    
    /**
     * Method used to get channel trading particular type of products
     * @param type of product sold in returned channel
     * @return channel trading type of product specified in parameter
     */
    public ProductChannel getChannel(ProductType type){
        ProductChannel tmp = this.channelMap.get(type);
        return tmp;
    }

    /**
     * Method used to get blockchain transaction channel
     * @return specific channel for transactions blockchain
     */
    public BlockchainChannel getBlockchainChannel(){
        return this.blockchain;
    }


    /**
     * Adding channel and type which it trades to market
     * @param channel to add
     * @param type traded in channel
     */
    public void addChannel(ProductChannel channel, ProductType type){
        this.channelMap.put(type, channel);
    }
    
    public void printMap(){
        if (this.channelMap.isEmpty()) { 
            System.out.println("Market is empty"); 
        } 
  
        else { 
            System.out.println(this.channelMap); 
        }
    }
    
    public int getNextOrderNumber(){
        if(orderNum == Integer.MAX_VALUE) orderNum = 0;
        return orderNum++;
    }
    
}
