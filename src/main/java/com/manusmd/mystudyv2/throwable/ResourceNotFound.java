package com.manusmd.mystudyv2.throwable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceNotFound extends Throwable {
    private String resource;
    private String id;
    public ResourceNotFound(String resource, String id) {
        this.resource = resource;
        this.id = id;
    }
}

