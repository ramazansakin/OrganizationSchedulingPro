package com.sakinramazan.micros.organizationschedulingpro.service;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.dto.Track;

import java.util.List;

public interface OrganizationService {

    // CRUD ops
    List<Organization> getAllOrganizations();

    Organization getOrganization(Integer id);

    Organization createOrganization(Organization organization);

    Organization updateOrganization(Organization organization);

    Event addEventToOrganization(Integer id, Event event);

    boolean deleteOrganization(Integer id);

    List<Track> scheduleEvents(Integer organization_id);

}
