package com.sakinramazan.micros.organizationschedulingpro;

import com.sakinramazan.micros.organizationschedulingpro.dao.EventRepository;
import com.sakinramazan.micros.organizationschedulingpro.dao.OrganizationRepository;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void whenFindBySubject_thenReturnSubject() {
        // Sample test event
        Event event = new Event();
        event.setSubject("Sample Presentation Event");
        event.setDuration(30);

        entityManager.persist(event);
        entityManager.flush();

        // find the event on the storage by subject
        Event found = eventRepository.getEventBySubject(event.getSubject());

        // then test
        assertThat(found.getSubject()).isEqualTo(event.getSubject());
    }

    @Test
    public void whenFindByName_thenReturnOrganization() {
        // Sample test event
        Organization organization = new Organization();
        organization.setName("Sample Conference");

        entityManager.persist(organization);
        entityManager.flush();

        // find the org on the storage by name
        Organization found = organizationRepository.getOrganizationByName(organization.getName());

        // then test
        assertThat(found.getName()).isEqualTo(organization.getName());
    }

}
