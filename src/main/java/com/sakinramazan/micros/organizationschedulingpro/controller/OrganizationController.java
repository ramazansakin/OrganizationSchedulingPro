package com.sakinramazan.micros.organizationschedulingpro.controller;

import com.sakinramazan.micros.organizationschedulingpro.dao.Event;
import com.sakinramazan.micros.organizationschedulingpro.dao.Organization;
import com.sakinramazan.micros.organizationschedulingpro.dto.OrganizationProgram;
import com.sakinramazan.micros.organizationschedulingpro.dto.Track;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<Organization> getOrganizationById(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);
        return ResponseEntity.ok().body(organization);
    }

    @PostMapping("/organizations")
    public Organization createOrganization(@Valid @RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @PutMapping("/organizations")
    public Organization updateOrganization(@Valid @RequestBody Organization organization) {
        return organizationService.updateOrganization(organization);
    }

    @PostMapping("/organizations/{id}/addevent")
    public Organization addEvent(@PathVariable Integer id, @Valid @RequestBody Event event) {
        return organizationService.addEventToOrganization(id, event);
    }


    @DeleteMapping("/organizations/{id}")
    public Map<String, Boolean> deleteOrganization(
            @PathVariable(value = "id") Integer id) {
        Organization organization = organizationService.getOrganization(id);

        organizationService.deleteOrganization(organization.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
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
