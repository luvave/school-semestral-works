package semestralka.core.channels;

import semestralka.core.entities.AbstractParty;
import semestralka.core.moneysystem.Transaction;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import semestralka.core.reports.TransactionReporter;

public class BlockchainChannel extends BasicChannel{
    private List<AbstractParty> nodes;
    private LinkedList<Transaction> blockchain;

    public BlockchainChannel(){
        parties = new LinkedList();
        nodes = new LinkedList();
        blockchain = new LinkedList();
    }

    public LinkedList<Transaction> getBlockchain(){
        return (LinkedList<Transaction>) this.blockchain.clone();
    }

    public boolean addNewTransaction(Transaction transaction){        
        if(notifyParties(transaction)){
            this.blockchain.add(transaction);
            giveNodesTransaction();
            return true;
        }
        return false;
    }

    private boolean notifyParties(Transaction transaction){
        for (AbstractParty party : nodes){
            if(!party.checkTransaction(transaction)){
                System.out.println("Error in blockchain!! You are probably hacked!");
                return false;
            }
        }
        return true;
    }

    private void giveNodesTransaction(){
        for (AbstractParty party : nodes){
            party.addTransactionToBlockchain(this.blockchain.getLast());
        }
    }
    
    public void accept(TransactionReporter visitor){
        visitor.visit(this);
    }
}
