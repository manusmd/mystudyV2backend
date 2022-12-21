package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.PresenceRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
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
        presenceLegit(presence);
    }

    public static PresenceModel presenceExists(String id, PresenceRepository presenceRepository) throws ResourceNotFound {
        Optional<PresenceModel> foundPresence = presenceRepository.findById(id);
        if(foundPresence.isEmpty()){
            throw new ResourceNotFound("Presence", id);
        }
        return foundPresence.get();
    }

    public static void presenceLegit(PresenceModel presence) throws ResourceConflict {
        if(presence.getIsExcused() && presence.getIsPresent()){
            throw new ResourceConflict("Cannot be excused and present at the same time");
        }
    }


}
