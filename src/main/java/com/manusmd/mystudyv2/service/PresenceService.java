package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.EventModel;
import com.manusmd.mystudyv2.model.PresenceModel;
import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.repository.PresenceRepository;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PresenceService {
    PresenceRepository presenceRepository;
    StudentRepository studentRepository;
    EventRepository eventRepository;

    public CustomResponse createPresence(PresenceModel presence) {
        try {
            StudentModel.studentExists(presence.getStudentId(), studentRepository);
            EventModel.eventExists(presence.getEventId(), eventRepository);
            PresenceModel.canCreate(presence, presenceRepository);
            PresenceModel createdPresence = presenceRepository.save(presence);
            return CustomResponse.CREATED(createdPresence, "Presence");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        } catch (ResourceConflict e) {
            return CustomResponse.CONFLICT(e.getMessage());
        }
    }

    public CustomResponse getPresence(String id) {
        try {
            PresenceModel foundPresence = PresenceModel.presenceExists(id, presenceRepository);
            return CustomResponse.FOUND(foundPresence, "Presence");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }
}
