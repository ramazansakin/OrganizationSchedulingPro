package com.sakinramazan.micros.organizationschedulingpro.service;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.EventDocument;

import java.util.List;

public interface EventService {

    public static final String NETWORKING_EVENT = "Networking Event";

    // CRUD ops
    List<Event> getAllEvents();

    Event getEvent(Integer id);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(Integer id);

    List<EventDocument> getEventsByOrganizationId(Integer organizationId);

    boolean produceEventDoc(Integer id);
}
