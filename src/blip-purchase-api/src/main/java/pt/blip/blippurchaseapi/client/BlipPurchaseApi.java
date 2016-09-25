package pt.blip.blippurchaseapi.client;

import java.util.Map;

/**
 * Blip Purchase API interface
 * 
 * @author Jose Rocha
 */
public interface BlipPurchaseApi {
    /**
     * Get purchase details
     * 
     * @param projection
     *            the optional query projection
     * @return result the result of the operation
     */
    public BlipPurchaseApiResult getPurchaseDetails(String projection);

    /**
     * Create or update purchase
     * 
     * @param id
     *            the id of the purchase
     * @param bodyParams
     *            the JSON request body
     * @return result the result of the operation
     */
    public BlipPurchaseApiResult createUpdatePurchase(String id, Map<String, Object> bodyParams);

    /**
     * Get purchase by ID
     * 
     * @param id
     *            the ID of the purchase
     * @return result the result of the operation
     */
    public BlipPurchaseApiResult getPurchaseById(String id);

    /**
     * Get purchase by product type
     * 
     * @param productType
     *            the product type of the purchase
     * @return result the result of the operation
     */
    public BlipPurchaseApiResult getPurchaseByProductType(String productType);

    /**
     * Get expired purchases
     * 
     * @return result the result of the operation
     */
    public BlipPurchaseApiResult getExpiredPurchases();
}
