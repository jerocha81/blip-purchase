package pt.blip.blippurchaseapi.exception;

/**
 * Blip Purchase API Exception class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseApiException extends Exception {
    private static final long serialVersionUID = -7671540267875320821L;

    public BlipPurchaseApiException() {
        super();
    }

    public BlipPurchaseApiException(String msg, Throwable e) {
        super(msg, e);
    }

    public BlipPurchaseApiException(String msg) {
        super(msg);
    }

    public BlipPurchaseApiException(Throwable e) {
        super(e);
    }
}
