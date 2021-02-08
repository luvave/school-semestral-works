/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.reports;

import java.util.LinkedList;
import java.util.List;
import semestralka.core.channels.BlockchainChannel;
import semestralka.core.entities.AbstractParty;
import semestralka.core.moneysystem.Transaction;

/**
 *
 * @author travnja5
 */
public class TransactionReporter extends AbstractReporter{
    List blockChain = new LinkedList();

    public TransactionReporter() {
        super();
    }

    public TransactionReporter(String filename) {
        super(filename);
    }

    public void visit(BlockchainChannel chan){
        List<Transaction> news = chan.getBlockchain();
        if(news.size() - blockChain.size()  > 0){
            writer.println("------Transaction realized during this day------");

            for(Transaction t : news){
                if(blockChain.contains(t)) continue;
                writer.println(t);
                writer.println("Seller: "+t.getSeller());
                writer.println("Buyer: "+ t.getBuyer());
                writer.println("Cost: "+t.getCost());
                writer.println("ProductType: "+ t.getType());
                writer.println();
                writer.flush();

            }
        }
        blockChain = news;    
    }
    
}
