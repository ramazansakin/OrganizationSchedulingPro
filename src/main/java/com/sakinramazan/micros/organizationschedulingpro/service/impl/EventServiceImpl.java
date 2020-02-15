package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.dao.EventRepository;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEvent(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found by id : " + id));
        return event;
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        Event upEvent = eventRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("User not found by id : " + event.getId()));

        upEvent.setSubject(event.getSubject());
        upEvent.setDuration(event.getDuration());

        return eventRepository.save(upEvent);
    }

    @Override
    public boolean deleteEvent(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            eventRepository.delete(event.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Event> getEventsByOrganizationId(Integer organization_id) {
        return eventRepository.getEventsByOrganization(organization_id);
    }
}
