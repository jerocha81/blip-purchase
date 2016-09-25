package pt.blip.blippurchaseapi.client;

import java.util.Map;

/**
 * Blip Purchase API Result class
 * 
 * @author Jose Rocha
 */
public class BlipPurchaseApiResult {
    public enum ErrorCode {
        SUCCESS(0, "Success"), 
        INVALID_REQUEST(1, "Invalid request"), 
        INVALID_ID(2, "Invalid id"),
        INVALID_PROJECTION(3, "Invalid projection"),
        INVALID_DATE(4, "Invalid date"),
        INTERNAL_ERROR(9, "Internal error");

        private final int code;
        private final String description;

        private ErrorCode(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return this.code;
        }

        public String getDescription() {
            return this.description;
        }
    }

    private ErrorCode errorCode;
    private Map<String, Object> document;

    public BlipPurchaseApiResult(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getDocument() {
        return this.document;
    }

    public void setDocument(Map<String, Object> document) {
        this.document = document;
    }
}
