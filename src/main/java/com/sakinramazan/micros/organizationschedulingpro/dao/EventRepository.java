package com.sakinramazan.micros.organizationschedulingpro.dao;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> getEventsByOrganization(Integer organizationId);

    Event getEventBySubject(String subject);

}
