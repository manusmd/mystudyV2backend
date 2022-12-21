package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Document("events")
public class EventModel {
    @Id
    private String id;
    @NonNull
    private String teacher;
    @NonNull
    private List<String> students;
    @NonNull
    private String name;
    @NonNull
    private String room;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDateTime;

    public boolean checkDateTimeLegit(EventModel event) {
        if (event.startDateTime.isAfter(this.startDateTime) && event.startDateTime.isBefore(this.endDateTime)) {
            return false;
        }
        return !event.endDateTime.isAfter(this.startDateTime) || !event.endDateTime.isBefore(this.endDateTime);
    }

    public boolean checkDateTimeLegit(List<EventModel> events) {
        List<EventModel> filteredEvents = events.stream()
                .filter(event -> event.startDateTime.isAfter(this.startDateTime) && event.startDateTime.isBefore(this.endDateTime) || event.endDateTime.isAfter(startDateTime) && event.endDateTime.isBefore(endDateTime))
                .toList();

        return filteredEvents.size() == 0;
    }

    public static void checkTeacherHasTime(EventModel event, EventRepository eventRepository) throws ResourceConflict {
        List<EventModel> events = eventRepository.findAllByTeacher(event.getTeacher());
        if (!event.checkDateTimeLegit(events)) {
            throw new ResourceConflict("Teacher " + event.getTeacher() + " has another event at this time");
        }
    }

    public static void checkStudentsHaveTime(EventModel event, EventRepository eventRepository) throws ResourceConflict {
        List<EventModel> foundStudentEvent = new ArrayList<>();
        List<String> blockedStudents = new ArrayList<>();

        event.getStudents()
                .forEach(student_id -> foundStudentEvent.addAll(eventRepository.findByStudentsContains(student_id)));

        List<EventModel> uniqueFoundStudentEvent = foundStudentEvent.stream().distinct().toList();

        for (EventModel studentEvent : uniqueFoundStudentEvent) {
            for (String student_id : event.getStudents()) {
                if (studentEvent.getStudents().contains(student_id) && !studentEvent.checkDateTimeLegit(event)) {
                    blockedStudents.add(student_id);
                }
            }
        }

        List<String> uniqueBlockedStudents = blockedStudents.stream().distinct().toList();

        if (!event.checkDateTimeLegit(foundStudentEvent)) {
            throw new ResourceConflict("Student(s) " + uniqueBlockedStudents.toString()
                    .replaceAll("\\[(.*?)\\]", "$1") + " has/have another event at this time");
        }
    }

    public static EventModel eventExists(String id, EventRepository eventRepository) throws ResourceNotFound {
        Optional<EventModel> foundEvent = eventRepository.findById(id);
        if (foundEvent.isEmpty()) {
            throw new ResourceNotFound("Event", id);
        }
        return foundEvent.get();
    }
}
