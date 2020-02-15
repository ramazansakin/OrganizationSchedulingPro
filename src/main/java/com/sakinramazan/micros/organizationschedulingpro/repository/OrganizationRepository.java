package com.sakinramazan.micros.organizationschedulingpro.repository;

import com.sakinramazan.micros.organizationschedulingpro.dao.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

}
