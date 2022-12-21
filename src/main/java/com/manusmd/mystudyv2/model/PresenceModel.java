package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.PresenceRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@Document("presences")
public class PresenceModel {
    @Id
    private String id;
    private String studentId;
    private String eventId;
    private Boolean isPresent;
    private Boolean isExcused;

    public static void canCreate(PresenceModel presence, PresenceRepository presenceRepository) throws ResourceExists, ResourceConflict {
        Optional<PresenceModel> foundPresence = presenceRepository.findByStudentIdAndEventId(presence.getStudentId(), presence.getEventId());
        if (foundPresence.isPresent()) {
            throw new ResourceExists(foundPresence.get(), "Presence", "StudentId and EventId");
        }
        if(presence.getIsExcused() && presence.getIsPresent()){
            throw new ResourceConflict("Cannot be excused and present at the same time");
        }

    }


}
