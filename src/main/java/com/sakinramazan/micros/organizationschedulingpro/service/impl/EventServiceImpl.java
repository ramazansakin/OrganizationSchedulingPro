package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.dao.EventElasticRepository;
import com.sakinramazan.micros.organizationschedulingpro.dao.EventRepository;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.EventDocument;
import com.sakinramazan.micros.organizationschedulingpro.exception.ResourceNotFoundException;
import com.sakinramazan.micros.organizationschedulingpro.kafka.KafkaMessageProducer;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventElasticRepository eventElasticRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    private ModelMapper modelMapper;

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
        EventDocument eventDoc = modelMapper.map(event, EventDocument.class);
        eventElasticRepository.save(eventDoc);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        Event upEvent = getEvent(event.getId());
        upEvent.setSubject(event.getSubject());
        upEvent.setDuration(event.getDuration());

        EventDocument eventDoc = modelMapper.map(upEvent, EventDocument.class);
        EventDocument oldSubject = eventElasticRepository.getBySubject(event.getSubject());
        eventElasticRepository.delete(oldSubject);
        eventElasticRepository.save(eventDoc);

        return eventRepository.save(upEvent);
    }

    @Override
    public boolean deleteEvent(Integer id) {
        Event event = getEvent(id);
        eventRepository.delete(event);
        eventElasticRepository.deleteBySubject(event.getSubject());
        return true;
    }

    @Override
    public List<EventDocument> getEventsByOrganizationId(Integer organizationId) {
        return eventElasticRepository.getAllByOrganization(organizationId);
    }

    @Override
    public boolean produceEventDoc(Integer id) {
        try {
            Event event = getEvent(id);
            EventDocument eventDoc = modelMapper.map(event, EventDocument.class);
            kafkaMessageProducer.produceEventDocument(eventDoc);
            return true;
        } catch (Exception ex) {
            log.error("Something s going wrong! [" + ex.getMessage() + "]");
            return false;
        }
    }
}
