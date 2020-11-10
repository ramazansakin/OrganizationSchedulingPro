package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.dao.EventElasticRepository;
import com.sakinramazan.micros.organizationschedulingpro.dao.EventRepository;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.EventDocument;
import com.sakinramazan.micros.organizationschedulingpro.exception.ResourceNotFoundException;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventElasticRepository eventElasticRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEvent(Integer id) {
        return eventRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found by id : " + id));
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        Event upEvent = eventRepository.findById(event.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found by id : " + event.getId()));

        upEvent.setSubject(event.getSubject());
        upEvent.setDuration(event.getDuration());

        return eventRepository.save(upEvent);
    }

    @Override
    public boolean deleteEvent(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
            return true;
        }
        return false;
    }

    @Override
    public List<EventDocument> getEventsByOrganizationId(Integer organizationId) {
        return eventElasticRepository.getAllByOrganization(organizationId);
    }
}
