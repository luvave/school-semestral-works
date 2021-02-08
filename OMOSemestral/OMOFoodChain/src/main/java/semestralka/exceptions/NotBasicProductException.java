/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.exceptions;

/**
 *
 * @author travnja5
 */
public class NotBasicProductException extends Exception {

    /**
     * Creates a new instance of <code>NotBasicProductException</code> without
     * detail message.
     */
    public NotBasicProductException() {
    }

    /**
     * Constructs an instance of <code>NotBasicProductException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotBasicProductException(String msg) {
        super(msg);
    }
}
