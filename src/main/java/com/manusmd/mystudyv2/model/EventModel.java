package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.mongodb.lang.NonNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        if (event.startDateTime.isAfter(startDateTime) && event.startDateTime.isBefore(endDateTime)) {
            return false;
        }
        return !event.endDateTime.isAfter(startDateTime) || !event.endDateTime.isBefore(endDateTime);
    }

    public boolean checkDateTimeLegit(List<EventModel> events) {
        List<EventModel> filteredEvents = events.stream()
                .filter(event -> event.startDateTime.isAfter(startDateTime) && event.startDateTime.isBefore(endDateTime) || event.endDateTime.isAfter(startDateTime) && event.endDateTime.isBefore(endDateTime))
                .toList();

        return filteredEvents.size() == 0;
    }

    public static void checkTeacherHasTime(EventModel event, String teacher, EventRepository eventRepository) throws ResourceConflict {
        List<EventModel> events = eventRepository.findAllByTeacher(teacher);
        if(!event.checkDateTimeLegit(events)){
            throw new ResourceConflict("Teacher " + teacher + " has another event at this time");
        }
    }

    public static void checkStudentsHaveTime(EventModel event, EventRepository eventRepository) throws ResourceConflict {
        List<EventModel> foundStudentEvent = new ArrayList<>();
        event.getStudents().forEach(student_id -> foundStudentEvent.addAll(eventRepository.findByStudentsContains(student_id)));
        List<String> blockedStudents = new ArrayList<>();
        for (EventModel studentEvent : foundStudentEvent) {
            if (!studentEvent.checkDateTimeLegit(event)) {
                blockedStudents.add(studentEvent.getStudents().get(0));
            }
        }

        if (!event.checkDateTimeLegit(foundStudentEvent)) {
            throw new ResourceConflict("Student " + blockedStudents + " has another event at this time");
        }
    }
}
