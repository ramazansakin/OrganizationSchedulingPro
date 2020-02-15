package com.sakinramazan.micros.organizationschedulingpro.service;

import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.model.Track;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrganizationService {

    // CRUD ops
    List<Organization> getAllOrganizations();

    Organization getOrganization(Integer id);

    Organization createOrganization(@RequestBody Organization organization);

    Organization updateOrganization(@RequestBody Organization organization);

    boolean deleteOrganization(Integer id);

    List<Track> scheduleEvents(Integer organization_id);

}
