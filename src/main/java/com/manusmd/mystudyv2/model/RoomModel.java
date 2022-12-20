package com.manusmd.mystudyv2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("rooms")
public class RoomModel {
    @Id
    private String id;
    private String name;
    private Integer capacity;
    private String notes;
}
