package com.manusmd.mystudyv2.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class CustomResponse {
    private String message;
    private Object data;
    private HttpStatus status;

    public CustomResponse(Object data, String message, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public static CustomResponse NOT_FOUND(String resource, String id) {
        String upperCaseResource = resource.toUpperCase();
        return new CustomResponse(null, String.format("%s %s not found", upperCaseResource, id), HttpStatus.NOT_FOUND);
    }

    public static CustomResponse CREATED(Object data, String resource) {
        String lowerCaseResource = resource.toLowerCase();
        return new CustomResponse(data, String.format("Successfully created %s", lowerCaseResource), HttpStatus.OK);
    }

    public static CustomResponse FOUND(Object data, String resource) {
        String upperCaseResource = resource.toUpperCase();
        return new CustomResponse(data, String.format("%s found", upperCaseResource), HttpStatus.OK);
    }

    public static CustomResponse FOUND_FETCHED_LIST(List<?> data, String resource) {
        String lowerCaseResource = resource.toLowerCase();
        return new CustomResponse(data, String.format("Successfully fetched %s %s/s ", data.size(), lowerCaseResource),
                HttpStatus.OK);
    }

    public static CustomResponse PUT_NOT_ALLOWED(String customMessage) {
        return new CustomResponse(null, customMessage, HttpStatus.CONFLICT);
    }

    public static CustomResponse OK_PUT(Object data, String resource) {
        String lowerCaseResource = resource.toLowerCase();
        return new CustomResponse(data, String.format("Successfully updated %s", lowerCaseResource),
                HttpStatus.OK);
    }
    public static CustomResponse OK_DELETE(String resource, String id) {
        String upperCaseResource = resource.toUpperCase();
        return new CustomResponse(null, String.format("%s %s deleted successfully!", upperCaseResource, id),
                HttpStatus.OK);
    }
    public static CustomResponse OK_DELETE(String resource, String id, String additionalMessage) {
        String upperCaseResource = resource.toUpperCase();
        return new CustomResponse(null, String.format("%s %s deleted successfully! %s", upperCaseResource, id,
                additionalMessage),
                HttpStatus.OK);
    }

    public static CustomResponse INTERNAL_SERVER_ERROR(String error) {
        return new CustomResponse(null, error, HttpStatus.OK);
    }


}
