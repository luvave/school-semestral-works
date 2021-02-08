/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.core.reports;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import semestralka.core.entities.AbstractParty;
import semestralka.core.product.ProductType;

/**
 *
 * @author travnja5
 */
public abstract class AbstractReporter {
    protected PrintWriter writer;
   
    
    /**
     * Create a reporter, who will print reports on standart output
     */
    public AbstractReporter(){
        writer = new PrintWriter(System.out);
    }
    
    /**
     * Create new reporter, who this daywill print a report to file
     * @param filename filename of report file
     */
    public AbstractReporter(String filename){
        newFile(filename);
    }
    
    /**
     * Use this method to change filename of reports
     * @param newFilename name of file to create report to
     */
    public final void newFile(String newFilename){
        try{
            writer = new PrintWriter(newFilename, "UTF-8");
        }
        catch(FileNotFoundException e ){
            System.err.println("Program was not given a permission to create a file!!");
            System.err.println("Switching output to standart out.");
            writer = new PrintWriter(System.out);
        }
        catch(UnsupportedEncodingException e){
            System.err.println("UTF-8 encoding not supported, no way of printing reports in file!!");
            System.err.println("Switching output to standart out.");
            writer = new PrintWriter(System.out);
        }
    }
}
