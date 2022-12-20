package com.manusmd.mystudyv2.throwable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

public class ResourceConflict extends Throwable {
    private String message;

    public ResourceConflict(String message) {
        this.message = message;
    }
}

