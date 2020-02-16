package com.sakinramazan.micros.organizationschedulingpro.controller;

import com.sakinramazan.micros.organizationschedulingpro.dto.OrganizationProgram;
import com.sakinramazan.micros.organizationschedulingpro.dto.Track;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.exception.InvalidRequestException;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/organizations")
    public ResponseEntity getAllOrganizations() {
        return new ResponseEntity(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<Organization> getOrganizationById(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);
        return ResponseEntity.ok().body(organization);
    }

    @PostMapping("/organizations")
    public ResponseEntity createOrganization(@Valid @RequestBody Organization organization, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Organization body");
        }
        return new ResponseEntity(organizationService.createOrganization(organization), HttpStatus.CREATED);
    }

    @PutMapping("/organizations")
    public ResponseEntity updateOrganization(@Valid @RequestBody Organization organization, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Organization body");
        }
        return new ResponseEntity(organizationService.updateOrganization(organization), HttpStatus.OK);
    }

    @PostMapping("/organizations/{id}/addevent")
    public ResponseEntity addEvent(@PathVariable Integer id, @Valid @RequestBody Event event, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidRequestException("Invalid request on Event body");
        }
        return new ResponseEntity(organizationService.addEventToOrganization(id, event), HttpStatus.OK);
    }


    @DeleteMapping("/organizations/{id}")
    public ResponseEntity deleteOrganization(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);

        organizationService.deleteOrganization(organization.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/organizations/schedule/{id}")
    public ResponseEntity<OrganizationProgram> getOrganizationProgram(@PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);
        List<Track> tracks = organizationService.scheduleEvents(organization.getId());

        OrganizationProgram program = new OrganizationProgram();
        program.setOrganizationName(organization.getName());
        program.setTracks(tracks);

        return ResponseEntity.ok().body(program);
    }

}
