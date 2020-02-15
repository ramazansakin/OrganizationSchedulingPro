package com.sakinramazan.micros.organizationschedulingpro.controller;


import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;


    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(
            @PathVariable(value = "id") Integer id) {
        Event event = eventService.getEvent(id);
        return ResponseEntity.ok().body(event);
    }

    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @DeleteMapping("/events/{id}")
    public Map<String, Boolean> deleteEvent(
            @PathVariable(value = "id") Integer id) {
        Event event = eventService.getEvent(id);

        eventService.deleteEvent(event.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


}
