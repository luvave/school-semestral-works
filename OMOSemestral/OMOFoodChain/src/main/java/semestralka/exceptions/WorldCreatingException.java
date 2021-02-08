package semestralka.exceptions;

public class WorldCreatingException extends Exception {

    /**
     * Creates a new instance of <code>WorldCreatingException</code> without
     * detail message.
     */
    public WorldCreatingException() {
    }

    /**
     * Constructs an instance of <code>WorldCreatingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WorldCreatingException(String msg) {
        super(msg);
    }
}

