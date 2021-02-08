/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.control;

import org.json.simple.JSONObject;
import semestralka.core.product.ProductType;
import semestralka.core.reports.PartyReporter;
import semestralka.core.reports.ProductReporter;
import semestralka.core.reports.TransactionReporter;
import semestralka.exceptions.WorldCreatingException;

/**
 * Basic API for usage
 * Prepared for GET and POST requests in future development
 * Returns and expects Json..
 * @author luvave
 */
public class API {
    private static PartyController parties = new PartyController();
    private static WorldController world = new WorldController(parties); //This remembers all options you set

    /**
     * Creates whole world based. Options clean or full
     * @param request json in format type: value
     * @return response in json format
     */
    //Set type: clean for clean world with no channels and ProductTypes, or full with every possible ProductTyp
    public static JSONObject postWorldOptions(JSONObject request){
        JSONObject response = new JSONObject();
        try {
            String type = (String)request.get("type");
            if(type.equals("clean")){
                world.makeCleanWorld();
            }
            if(type.equals("full")){
                world.makeFullWorld();
            }
            response.put("message","success, type "+type+" is set");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Set reporters
     * @param request json in format type: value
     * @return response in json format
     */

    public static JSONObject postSetReportOptions(JSONObject request){
        JSONObject response = new JSONObject();
        try {
            String type = (String)request.get("type");
            if(type.equals("party")){
                world.setPartyReporter(new PartyReporter("partyReport.txt"));
            }
            if(type.equals("product")){
                world.setProductReporter(new ProductReporter("productReport.txt"));
            }
            if(type.equals("transaction")){
                world.setTransReporter(new TransactionReporter("transactionReport.txt"));
            }
            if(type.equals("full")){
                world.setPartyReporter(new PartyReporter("partyReport.txt"));
                world.setProductReporter(new ProductReporter("productReport.txt"));
                world.setTransReporter(new TransactionReporter("transactionReport.txt"));
            }
            response.put("message","success, type "+type+" is set");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Run simulation for given days
     * @param request json in format days: int
     * @return response in json format
     */

    public static JSONObject postSetDays(JSONObject request){
        JSONObject response = new JSONObject();
        try {
            int days = (int)request.get("days");
            world.runForDays(days);
            response.put("message","success "+days+" have been run");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Send order for a product as consumer
     * @param request json in format product: productType
     * @return response in json format
     */
    public static JSONObject postConsumerOrder(JSONObject request){
        JSONObject response = new JSONObject();
        try {
            ProductType productType = (ProductType)request.get("product");
            world.consumerOrder(productType);
            response.put("message","success "+productType+" have been ordered");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Generate a transaction report to a file
     * @return response in json format
     */
    public static JSONObject getTransactionReports(){
        JSONObject response = new JSONObject();
        try {
            world.transactionReport();
            response.put("message","success transaction reports will generate after each day to a file transactionReport.txt");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Generate a party report to a file
     * @return response in json format
     */
    public static JSONObject getPartyReport(){
        JSONObject response = new JSONObject();
        try {
            world.partyReport();
            response.put("message","success party report will generate to a file partyReport.txt");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }

    /**
     * Generate a product report to a file
     * @return response in json format
     */
    public static JSONObject getProductReport(){
        JSONObject response = new JSONObject();
        try {
            world.productReport();
            response.put("message","success product report will generate to a file productReport.txt");
        }
        catch (Exception e){
            response.put("error","wrong input data");
        }
        return response;
    }
}

