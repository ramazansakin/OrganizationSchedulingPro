package com.sakinramazan.micros.organizationschedulingpro.repository;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
