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

import java.util.Objects;

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
            EventModel.checkTeacherHasTime(event, eventRepository);
            EventModel.checkStudentsHaveTime(event, eventRepository);
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

    public CustomResponse getEvent(String id) {
        try {
            EventModel foundEvent = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Event", id));
            return CustomResponse.FOUND(foundEvent, "Event");
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getMessage());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getEvents() {
        try {
            return CustomResponse.FOUND_FETCHED_LIST(eventRepository.findAll(), "Events");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse updateEvent(String id, EventModel event) {
        try {
            EventModel foundEvent = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Event", id));
            if (!Objects.equals(event.getTeacher(), foundEvent.getTeacher())) {
                TeacherModel.teacherExists(event.getTeacher(), teacherRepository);
                EventModel.checkTeacherHasTime(event, eventRepository);
                foundEvent.setTeacher(event.getTeacher());
            }
            if (!event.getStudents().equals(foundEvent.getStudents())) {
                StudentModel.studentsExist(event.getStudents(), studentRepository);
                EventModel.checkStudentsHaveTime(event, eventRepository);
                foundEvent.setStudents(event.getStudents());
            }
            if (!event.getRoom().equals(foundEvent.getRoom())) {
                RoomModel.isRoomAvailable(event, roomRepository, eventRepository);
                foundEvent.setRoom(event.getRoom());
            }
            if (!event.getStartDateTime().equals(foundEvent.getStartDateTime())) {
                EventModel.checkTeacherHasTime(event, eventRepository);
                EventModel.checkStudentsHaveTime(event, eventRepository);
                RoomModel.isRoomAvailable(event, roomRepository, eventRepository);
                foundEvent.setStartDateTime(event.getStartDateTime());
            }
            if (!event.getEndDateTime().equals(foundEvent.getEndDateTime())) {
                EventModel.checkTeacherHasTime(event, eventRepository);
                EventModel.checkStudentsHaveTime(event, eventRepository);
                RoomModel.isRoomAvailable(event, roomRepository, eventRepository);
                foundEvent.setEndDateTime(event.getEndDateTime());
            }
            EventModel updatedEvent = eventRepository.save(foundEvent);
            return CustomResponse.OK_PUT(updatedEvent, "Event");
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getMessage());
        } catch (ResourceConflict e) {
            return CustomResponse.CONFLICT(e.getMessage());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}
