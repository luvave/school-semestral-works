package semestralka.control;

import semestralka.core.entities.AbstractParty;
import semestralka.core.entities.Consumer;
import semestralka.core.entities.Market;
import semestralka.core.product.ProductType;
import semestralka.core.reports.PartyReporter;
import semestralka.core.reports.ProductReporter;
import semestralka.core.reports.TransactionReporter;

import java.util.List;
import semestralka.core.channels.ProductChannel;

/**
 * Is used by API
 * Main purpose of this is to remember current world options.
 * @author luvave
 */
public class WorldController {

    private PartyController partyController;
    private ActionController actionController;

    private Market market;
    private TransactionReporter transReporter;
    private TransactionReporter acctualTransReported = null;
    private ProductReporter productReporter;
    private PartyReporter partyReporter;
    
    public WorldController(PartyController partyController){
        this.partyController = partyController;
        actionController = new ActionController();
        market = Market.getInstance();
        
        productReporter = new ProductReporter("products.txt");
        partyReporter = new PartyReporter("parties.txt");
        transReporter = new TransactionReporter("transactions.txt");
    }
    
    
        /**
     * Creates market
     * @return an instance of market
     */
    public Market createBasicMarket(){
        return market;
    }
    /**
     * Creates market with all Channels
     * @return instance of market
     */
    public Market createFullMarket(){
        createAllChannels(market);
        return market;
    }
    /**
     * Generate all avaliable channels to the market
     * @param market to what market?
     */
    public void createAllChannels(Market market){
        for(ProductType t : ProductType.values()){
            market.addChannel(new ProductChannel(t,market),t);
        }
    }
    /**
     * Creates world with all types of parties but no products
     */
    public void makeCleanWorld(){
        market = createBasicMarket();
        partyController.createBasicFarmer(market,"Farmer",10);
        partyController.createBasicProccessor(market,"Processor",10);
        partyController.createBasicStore(market,"Store", 10);
        partyController.createBasicLogistics(market, "Logistics", 10);
        partyController.createBasicDistributor(market, "Distributor", 10);
        partyController.createBasicConsumer(market, 1000, "Consumer");        
    }

    /**
     * Creates world with all types of parties and all products
     */
    public void makeFullWorld(){
        market = createFullMarket();
        partyController.createFullFarmer(market,"Farmer",10);
        partyController.createFullProccessor(market,"Processor",10);
        partyController.createFullStore(market,"Store", 10);
        partyController.createFullLogistics(market, "Logistics", 10);
        partyController.createFullDistributor(market, "Distributor", 10);
        partyController.createRichConsumer(market, "Consumer");
    }

    /**
     * Run simulation for x days
     * @param days int
     */
    public void runForDays(int days){
        actionController.DayCycle(partyController.getCreatedParties(),days,transReporter,market);
    }

    /**
     * Order product as consumer
     * @param productType what product?
     */
    public void consumerOrder(ProductType productType){
        actionController.makeOrder(productType, partyController.getCreatedParties());
    }


    /**
     * Generate report
     */
    public void partyReport(){
        if(this.partyReporter!=null){
            partyReporter.report(partyController.getCreatedParties(), actionController.getDayNumber());
        }
    }

    /**
     * Generate report
     */
    public void productReport(){
        if(this.productReporter!=null){
            productReporter.report(partyController.getCreatedParties(), actionController.getDayNumber());
        }
    }
    
    public void productAndPartyReport(){
        productReport();
        partyReport();
    }

    public List<AbstractParty> getParties() {
        return partyController.getCreatedParties();
    }
    
    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public TransactionReporter getTransReporter() {
        return transReporter;
    }


    public ProductReporter getProductReporter() {
        return productReporter;
    }


    public PartyReporter getPartyReporter() {
        return partyReporter;
    }


    void transactionReport() {
        
    }

    void setTransReporter(TransactionReporter transactionReporter) {
        this.transReporter = transactionReporter;
    }

    void setPartyReporter(PartyReporter partyReporter) {
        this.partyReporter = partyReporter;
    }

    void setProductReporter(ProductReporter productReporter) {
        this.productReporter = productReporter;
    }
    
}
