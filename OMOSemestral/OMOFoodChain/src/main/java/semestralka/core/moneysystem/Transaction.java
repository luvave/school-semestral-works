package semestralka.core.moneysystem;

import javax.naming.directory.InvalidAttributeValueException;
import semestralka.core.entities.AbstractParty;
import semestralka.core.orders.Order;
import semestralka.core.product.Product;
import semestralka.core.product.ProductType;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 *
 * @author luvave
 */
public class Transaction {
    private String Hash;
    private String prevHash;
    private AbstractParty buyer, seller;
    private ProductType type;
    private Money cost;
    private boolean accepted, buyerAccountSet, sellerAccountSet;
    private Money buyerAccount, sellerAccount;
    
    
    /**
     * <p>This constructor should be used when the submitter of the order (buyer) is buying from /other/ party (seller).</p>
     * <p>The other party must use SetProduct() method afterwards to specify product, that should be traded.</p>
     * @param order Order submitted by seller. On this order is transaction information based
     * @param other Party, who is buying certain product from other Party 
     * @param buyerAccount balance of buyer, from which will be the Money taken
     */
    public Transaction(Order order, AbstractParty other, Money submitterAccount){
        type = order.getProductType();
        cost = order.getTotalCost();
        if(order.isSelling()){
            buyer = other;
            sellerAccount = submitterAccount;
            seller = order.getSubmitter();
            sellerAccountSet = true;
        }
        else{
            buyer = order.getSubmitter();
            buyerAccount = submitterAccount;
            seller = other;
            buyerAccountSet = true;
        }
        
        generateHash();
    }
    
    public final void setOtherAccount(Money othersBalance){
        if(!buyerAccountSet){
            this.buyerAccount = othersBalance;
            buyerAccountSet = true;
        }
        else if(!sellerAccountSet){
            this.sellerAccount = othersBalance;
            sellerAccountSet = true;
        }
        generateHash();
    }
    
    

    public Money getCost() {
        return cost;
    }
    public ProductType getType(){
        return type;
    }
    public boolean isAccepted(){
        return accepted;
    }
    public AbstractParty getSeller(){
        return seller;
    }
    public AbstractParty getBuyer(){
        return buyer;
    }
    
// -------------------------- GENERATING HASH ------------------------------- //
    private final void generateHash() {
        String hashnum = Integer.toString(hashCodeGenerator());
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not load algorithm SHA-256");
        }
        byte[] tmpHash = md.digest(hashnum.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, tmpHash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        this.Hash = hexString.toString();
    }

    private int hashCodeGenerator() {
        return Objects.hash(buyer, seller, type, cost, buyerAccountSet, sellerAccountSet, buyerAccount, sellerAccount);
    }

    public String getHash(){
        return this.Hash;
    }
    
    /**
     * finishes transaction after beeing approved by all memebers of blockchain channel
     * @return true on succesful money transfer
     */
    public boolean realize(){
        if(!accepted && sellerAccountSet && buyerAccountSet){
            accepted = true;
            return buyerAccount.payTo(cost, sellerAccount);
        }
        System.err.println("YOU ARE TRYING TO SEND THE TRANSACTION AGAIN!");
        return false;
    }

    /**
     * 
     * @return a hash of previous transaction in blockchain
     */
    public String getPrevHash() {
        return prevHash;
    }
    
    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Transaction other = (Transaction) obj;
        if (!Objects.equals(this.Hash, other.Hash)) {
            return false;
        }
        if (!Objects.equals(this.prevHash, other.prevHash)) {
            return false;
        }
        if (!Objects.equals(this.buyer, other.buyer)) {
            return false;
        }
        if (!Objects.equals(this.seller, other.seller)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction: "+ Hash;
    }
    
    
    
    
    
}
