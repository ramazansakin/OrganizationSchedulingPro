package com.sakinramazan.micros.organizationschedulingpro.dao;

import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    Organization getOrganizationByName(String name);

}
