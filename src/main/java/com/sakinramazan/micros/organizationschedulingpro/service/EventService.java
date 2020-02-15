package com.sakinramazan.micros.organizationschedulingpro.service;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EventService {

    // CRUD ops
    List<Event> getAllEvents();

    Event getEvent(Integer id);

    Event createEvent(@RequestBody Event event);

    Event updateEvent(@RequestBody Event event);

    boolean deleteEvent(Integer id);

}
