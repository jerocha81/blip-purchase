package pt.blip.blippurchaseservice.exception;

/**
 * Blip Purchase Exception class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseException extends Exception {
    private static final long serialVersionUID = -3196648553584230884L;

    public BlipPurchaseException() {
        super();
    }

    public BlipPurchaseException(String msg, Throwable e) {
        super(msg, e);
    }

    public BlipPurchaseException(String msg) {
        super(msg);
    }

    public BlipPurchaseException(Throwable e) {
        super(e);
    }
}
