package com.sakinramazan.micros.organizationschedulingpro.controller;

import com.sakinramazan.micros.organizationschedulingpro.dto.EventDTO;
import com.sakinramazan.micros.organizationschedulingpro.dto.OrganizationProgram;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.exception.InvalidRequestException;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);
        return ResponseEntity.ok().body(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@Valid @RequestBody Organization organization, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Organization body");
        }
        return new ResponseEntity<>(organizationService.createOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Organization> updateOrganization(@Valid @RequestBody Organization organization, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Organization body");
        }
        return new ResponseEntity<>(organizationService.updateOrganization(organization), HttpStatus.OK);
    }

    @PostMapping("/{id}/add_event")
    public ResponseEntity<Event> addEvent(@PathVariable Integer id, @Valid @RequestBody EventDTO event, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Event body");
        }
        // Mapping eventDTO to Event
        Event requestEvent = new Event();
        requestEvent.setOrganization(organizationService.getOrganization(id));
        requestEvent.setSubject(event.getSubject());
        requestEvent.setDuration(Integer.parseInt(event.getDuration()));

        return new ResponseEntity<>(organizationService.addEventToOrganization(id, requestEvent), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrganization(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);

        organizationService.deleteOrganization(organization.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<OrganizationProgram> getOrganizationProgram(@PathVariable(value = "id") Integer id) {
        OrganizationProgram organizationProgram = organizationService.scheduleEvents(id);
        return ResponseEntity.ok().body(organizationProgram);
    }

}
