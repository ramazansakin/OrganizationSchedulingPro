package com.sakinramazan.micros.organizationschedulingpro.controller;


import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.exception.InvalidRequestException;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="*")
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public ResponseEntity getAllEvents() {
        return new ResponseEntity(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity getEventById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(eventService.getEvent(id));
    }

    @PostMapping("/events")
    public ResponseEntity createEvent(@Valid @RequestBody Event event, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Event body");
        }
        return new ResponseEntity(eventService.createEvent(event), HttpStatus.CREATED);
    }

    @PutMapping("/events")
    public ResponseEntity updateEvent(@Valid @RequestBody Event event, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Event body");
        }
        return new ResponseEntity(eventService.updateEvent(event), HttpStatus.OK);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity deleteEvent(@PathVariable(value = "id") Integer id) {
        Event event = eventService.getEvent(id);
        eventService.deleteEvent(event.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
