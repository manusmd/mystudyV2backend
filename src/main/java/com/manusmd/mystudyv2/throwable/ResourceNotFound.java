package com.manusmd.mystudyv2.throwable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceNotFound extends Throwable {
    private String resource;
    private String message;
    public ResourceNotFound(String resource, String message) {
        this.resource = resource;
        this.message = message;
    }
}

