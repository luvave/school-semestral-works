/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.moneysystem;

/**
 * Class representing money. As a currency is used money unit [M].
 * @author luvave
 */
public class Money {
    private int amount;
    
    public Money(int value){
        amount = value;
    }
    
    public int getAmount(){
        return amount;
    }
    
    private boolean substract(Money money){
        if(ableToPay(money) && money.getAmount() >= 0){
            this.amount -= money.getAmount();
            return true;
        }
        System.err.println("Transaction could not be realised due to not enough "
                + "money in account or because of negative value of parameter");
        return false;
    }
    
    /**
     * Method to compare two money values
     * @param money
     * @return 
     */
    public boolean ableToPay(Money money){
        if(money.getAmount() < 0) return false;
        return this.amount >= money.getAmount();
    }
    
    private void add(Money money){
        if(money.getAmount() >= 0)
            this.amount += money.getAmount();
    }

    @Override
    public String toString() {
        return amount + "M";
    }

    @Override
    public int hashCode() {
        int hash = amount * 13;
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
        final Money other = (Money) obj;
        if (this.amount != other.amount) {
            return false;
        }
        return true;
    }
    
    /**
     * Paying certain amount of money to another Account
     * @param amount Money value to be paid
     * @param account other party's balance account
     * @return 
     */
    public boolean payTo(Money amount, Money account){
        if(!this.ableToPay(amount))return false;
        this.substract(amount);
        account.add(amount);
        return true;
    }
    
    
    
}
