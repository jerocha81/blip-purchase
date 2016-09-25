package pt.blip.blipmongolib.exception;

/**
 * Mongo Exception class
 * 
 * @author Jose Rocha
 */
public class MongoException extends Exception {
    private static final long serialVersionUID = 1432481718190324740L;

    public MongoException() {
        super();
    }

    public MongoException(String msg, Throwable e) {
        super(msg, e);
    }

    public MongoException(String msg) {
        super(msg);
    }

    public MongoException(Throwable e) {
        super(e);
    }
}
