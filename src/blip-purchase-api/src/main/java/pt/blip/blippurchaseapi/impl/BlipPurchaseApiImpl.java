package pt.blip.blippurchaseapi.impl;

import java.util.Date;
import java.util.Map;

import org.bson.Document;
import org.joda.time.DateTime;

import com.google.gson.internal.LinkedTreeMap;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import pt.blip.blipmongolib.Mongo;
import pt.blip.blipmongolib.exception.MongoException;
import pt.blip.blippurchaseapi.client.BlipPurchaseApi;
import pt.blip.blippurchaseapi.client.BlipPurchaseApiResult;
import pt.blip.blippurchaseapi.client.BlipPurchaseApiResult.ErrorCode;
import pt.blip.blippurchaseapi.exception.BlipPurchaseApiException;

/**
 * Blip Purchase API implementation class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseApiImpl implements BlipPurchaseApi {
    private Mongo mongo;

    public BlipPurchaseApiImpl() {

    }

    public void init(String dbUri, String dbName) throws BlipPurchaseApiException {
        try {
            this.mongo = new Mongo(dbUri, dbName);
        } catch (MongoException e) {
            throw new BlipPurchaseApiException("Error creating MongoDB client", e);
        }
    }

    public BlipPurchaseApiResult getPurchaseDetails(String projection) {
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        FindIterable<Document> iterable = null;
        Document document = null;
        try {
            // get all purchases with valid expire date, sorted by expire date ascending, and return their details
            iterable = this.mongo.find("purchase", Filters.gt("expires", new Date())).sort(new BasicDBObject("expires", 1));

            // set query projection
            Document projectionDoc = null;
            if (projection != null) {
                try {
                    projectionDoc = setProjection(projection);
                } catch (BlipPurchaseApiException e) {
                    result.setErrorCode(ErrorCode.INVALID_PROJECTION);
                    return result;
                }
            } else {
                // the default projection excludes internal field '_id'
                projectionDoc = new Document("_id", 0);
            }

            iterable = iterable.projection(projectionDoc);
        } catch (MongoException e) {
            result.setErrorCode(ErrorCode.INTERNAL_ERROR);
            return result;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{result: [");
        int i = 0;
        for (Document doc : iterable) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(doc.toJson());
            i++;
        }
        sb.append("]}");

        document = Document.parse(sb.toString());
        result.setDocument(document);

        return result;
    }

    public BlipPurchaseApiResult createUpdatePurchase(String id, Map<String, Object> bodyParams) {
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        // convert body fields to their correct types
        Object obj = bodyParams.get("expires");
        if (obj != null) {
            try {
                bodyParams.put("expires", new DateTime(obj.toString()).toDate());
            } catch (Exception e) {
                result.setErrorCode(ErrorCode.INVALID_DATE);
                return result;
            }
        }
        obj = bodyParams.get("id");
        if (obj != null) {
            bodyParams.put("id", Long.valueOf((String) obj));
        }
        LinkedTreeMap<String, Object> details = (LinkedTreeMap<String, Object>) bodyParams.get("purchaseDetails");
        obj = details.get("id");
        if (obj != null) {
            details.put("id", Long.valueOf((String) obj));
        }
        obj = details.get("quantity");
        if (obj != null) {
            details.put("quantity", Integer.valueOf((String) obj));
        }

        try {
            this.mongo.insertOrUpdateDocument("purchase", Filters.eq("id", Long.parseLong(id)), new Document(bodyParams));
        } catch (MongoException e) {
            result.setErrorCode(ErrorCode.INTERNAL_ERROR);
            return result;
        }

        return result;
    }

    public BlipPurchaseApiResult getPurchaseById(String id) {
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        FindIterable<Document> iterable = null;
        Document document = null;
        try {
            // get purchase with specified ID
            iterable = this.mongo.find("purchase", Filters.eq("id", Long.parseLong(id)));

            // set default projection
            Document projectionDoc = new Document("_id", 0);

            iterable = iterable.projection(projectionDoc);
        } catch (MongoException e) {
            result.setErrorCode(ErrorCode.INTERNAL_ERROR);
            return result;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{result: [");
        int i = 0;
        for (Document doc : iterable) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(doc.toJson());
            i++;
        }
        sb.append("]}");

        document = Document.parse(sb.toString());
        result.setDocument(document);

        return result;
    }

    public BlipPurchaseApiResult getPurchaseByProductType(String productType) {
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        FindIterable<Document> iterable = null;
        Document document = null;
        try {
            // get purchase with specified ID
            iterable = this.mongo.find("purchase", Filters.eq("productType", productType));

            // set default projection
            Document projectionDoc = new Document("_id", 0);

            iterable = iterable.projection(projectionDoc);
        } catch (MongoException e) {
            result.setErrorCode(ErrorCode.INTERNAL_ERROR);
            return result;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{result: [");
        int i = 0;
        for (Document doc : iterable) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(doc.toJson());
            i++;
        }
        sb.append("]}");

        document = Document.parse(sb.toString());
        result.setDocument(document);

        return result;
    }

    public BlipPurchaseApiResult getExpiredPurchases() {
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        FindIterable<Document> iterable = null;
        Document document = null;
        try {
            // get all purchases that have expired, sorted by expire date descending, and return their details
            iterable = this.mongo.find("purchase", Filters.lte("expires", new Date())).sort(new BasicDBObject("expires", -1));

            // set query projection
            Document projectionDoc = new Document("_id", 0);

            iterable = iterable.projection(projectionDoc);
        } catch (MongoException e) {
            result.setErrorCode(ErrorCode.INTERNAL_ERROR);
            return result;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{result: [");
        int i = 0;
        for (Document doc : iterable) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(doc.toJson());
            i++;
        }
        sb.append("]}");

        document = Document.parse(sb.toString());
        result.setDocument(document);

        return result;
    }

    /**
     * Set query projection
     * 
     * @param projection
     * @return
     */
    private Document setProjection(String projection) throws BlipPurchaseApiException {
        Document projectionDoc = new Document();
        if (projection != null && !projection.isEmpty()) {
            String[] projections = projection.split(";");
            for (int i = 0; i < projections.length; i++) {
                String[] projectionKeys = projections[i].split("\\=");
                if (projectionKeys.length == 2 && (projectionKeys[1].equals("0") || projectionKeys[1].equals("1"))) {
                    projectionDoc.append(projectionKeys[0], Integer.valueOf(projectionKeys[1]));
                } else {
                    throw new BlipPurchaseApiException("Invalid projection [" + projection + "]");
                }
            }
        }

        return projectionDoc;
    }
}
