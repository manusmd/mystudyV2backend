package com.manusmd.mystudyv2.throwable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceExists extends Throwable {
    private Object resource;
    private String resourceName;
    private String checkedProperty;

    public ResourceExists(Object resource, String resourceName, String checkedProperty) {
        this.resource = resource;
        this.resourceName = resourceName;
        this.checkedProperty = checkedProperty;
    }
}

