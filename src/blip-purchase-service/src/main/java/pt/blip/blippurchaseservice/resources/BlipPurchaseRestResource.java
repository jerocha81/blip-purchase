package pt.blip.blippurchaseservice.resources;

import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import pt.blip.blippurchaseapi.client.BlipPurchaseApi;
import pt.blip.blippurchaseapi.client.BlipPurchaseApiResult;
import pt.blip.blippurchaseapi.client.BlipPurchaseApiResult.ErrorCode;
import pt.blip.blippurchaseservice.exception.BlipPurchaseException;

/**
 * Blip Purchase REST resource class
 * 
 * @author Jose Rocha
 */
@Path("/blipstorews")
public class BlipPurchaseRestResource {
    private static final Log log = LogFactory.getLog(BlipPurchaseRestResource.class);
    private BlipPurchaseApi blipPurchaseApi;
    private Gson gson;
    private Type type;

    public BlipPurchaseRestResource(BlipPurchaseApi blipPurchaseApi) throws BlipPurchaseException {
        this.blipPurchaseApi = blipPurchaseApi;
        this.gson = new Gson();
        this.type = new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    /**
     * Get purchase details
     * 
     * @param response
     * @return response
     */
    @GET
    @Path("/purchases")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchaseDetails(@HeaderParam(value = "projection") String projection, @Context final HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Status status = Status.OK;
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        result = this.blipPurchaseApi.getPurchaseDetails(projection);

        return Response.status(status).entity(buildResponse(result, status, null, start, true)).build();
    }

    /**
     * Create or update purchase
     * 
     * @param id
     *            the purchase ID
     * @param body
     *            the request JSON body
     * @param response
     * @return response
     */
    @PUT
    @Path("/purchases/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUpdatePurchase(@PathParam("id") String id, String body, @Context final HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Status status = Status.ACCEPTED;
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);
        String msg = null;

        if (id == null) {
            status = Status.BAD_REQUEST;
            result.setErrorCode(ErrorCode.INVALID_REQUEST);
            return Response.status(status).entity(buildResponse(result, status, null, start, false)).build();
        }

        msg = "Request body: " + body.replaceAll("\\s+", " ");

        Map<String, Object> bodyMap = this.documentToMap(body);
        String bodyId = (String) bodyMap.get("id");
        if (bodyId == null || !bodyId.equals(id)) {
            status = Status.BAD_REQUEST;
            result.setErrorCode(ErrorCode.INVALID_ID);
            return Response.status(status).entity(buildResponse(result, status, null, start, false)).build();
        }

        result = this.blipPurchaseApi.createUpdatePurchase(id, bodyMap);

        try {
            response.flushBuffer();
        } catch (Exception e) {
            log.error("Error flushing buffer" + e);
        }

        return Response.status(status).entity(buildResponse(result, status, msg, start, false)).build();
    }

    /**
     * Get purchase by ID
     * 
     * @param id
     *            the ID of the purchase
     * @param response
     * @return document
     */
    @GET
    @Path("/purchases/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchaseById(@PathParam("id") String id, @Context final HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Status status = Status.OK;
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        result = this.blipPurchaseApi.getPurchaseById(id);

        return Response.status(status).entity(buildResponse(result, status, null, start, true)).build();
    }

    /**
     * Get purchase by product type
     * 
     * @param productType
     *            the product type of the purchase
     * @param response
     * @return document
     */
    @GET
    @Path("/purchases/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchaseByProductType(@PathParam("type") String productType, @Context final HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Status status = Status.OK;
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        result = this.blipPurchaseApi.getPurchaseByProductType(productType);

        return Response.status(status).entity(buildResponse(result, status, null, start, true)).build();
    }

    /**
     * Get expired purchases
     * 
     * @param productType
     *            the product type of the purchase
     * @param response
     * @return document
     */
    @GET
    @Path("/purchases/expired")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExpiredPurchases(@Context final HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Status status = Status.OK;
        BlipPurchaseApiResult result = new BlipPurchaseApiResult(ErrorCode.SUCCESS);

        result = this.blipPurchaseApi.getExpiredPurchases();

        return Response.status(status).entity(buildResponse(result, status, null, start, true)).build();
    }

    /**
     * Build JSON response
     * 
     * @param result
     * @param status
     * @param msg
     * @param start
     * @param isGet
     * @return document
     */
    private Document buildResponse(BlipPurchaseApiResult result, Status status, String msg, long start, boolean isGet) {
        Document document = null;
        if (status.equals(Status.OK) && isGet && result.getDocument() != null) {
            document = new Document(result.getDocument());
        } else {
            document = new Document("code", String.valueOf(result.getErrorCode().getCode())).append("description", result.getErrorCode().getDescription());
        }

        log.info(msg + " [" + result.getErrorCode().getCode() + ", " + result.getErrorCode().getDescription() + "] [" + status.getStatusCode() + "] (Time: " + (System.currentTimeMillis() - start)
                + " ms)");

        return document;
    }

    /**
     * Parse body document into a map
     * 
     * @param body
     *            the request JSON body
     * @return map
     * @throws JsonParseException
     * @throws JsonSyntaxException
     */
    private Map<String, Object> documentToMap(String body) throws JsonParseException, JsonSyntaxException {
        return this.gson.fromJson(body, type);
    }
}
