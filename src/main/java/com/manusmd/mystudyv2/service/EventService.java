package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.EventModel;
import com.manusmd.mystudyv2.model.RoomModel;
import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.repository.RoomRepository;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventService {
    EventRepository eventRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    RoomRepository roomRepository;

    public CustomResponse createEvent(EventModel event) {
        try {
            StudentModel.studentsExist(event.getStudents(), studentRepository);
            TeacherModel.teacherExists(event.getTeacher(), teacherRepository);
            EventModel.checkTeacherHasTime(event, event.getTeacher(), eventRepository);
            EventModel.checkStudentsHasTime(event, eventRepository);
            RoomModel.isRoomAvailable(event, roomRepository, eventRepository);
            EventModel newEvent = eventRepository.save(event);
            return CustomResponse.CREATED(newEvent, "Event");
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getMessage());
        } catch (ResourceConflict e) {
            return CustomResponse.CONFLICT(e.getMessage());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }

    }
}
