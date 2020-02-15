package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.repository.OrganizationRepository;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Organization getOrganization(Integer id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Organization Not found bby id : " + id));

        return organization;
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        Organization upOrganization = getOrganization(organization.getId());

        upOrganization.setName(organization.getName());
        upOrganization.setEvents(organization.getEvents());

        return organizationRepository.save(upOrganization);
    }

    @Override
    public boolean deleteOrganization(Integer id) {
        Organization organization = getOrganization(id);
        organizationRepository.delete(organization);
        return true;
    }
}
