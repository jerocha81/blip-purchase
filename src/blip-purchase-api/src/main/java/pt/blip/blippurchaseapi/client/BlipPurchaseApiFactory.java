package pt.blip.blippurchaseapi.client;

import pt.blip.blippurchaseapi.exception.BlipPurchaseApiException;
import pt.blip.blippurchaseapi.impl.BlipPurchaseApiImpl;

/**
 * Blip Purchase API Factory class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseApiFactory {
    public static BlipPurchaseApi getInstance(String dbUri, String dbName) throws BlipPurchaseApiException {
        BlipPurchaseApiImpl blipPurchaseApiImpl = new BlipPurchaseApiImpl();
        blipPurchaseApiImpl.init(dbUri, dbName);
        
        return blipPurchaseApiImpl;
    }
}
