package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.model.Track;
import com.sakinramazan.micros.organizationschedulingpro.repository.OrganizationRepository;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EventService eventService;

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

    @Override
    public List<Track> scheduleEvents(Integer organization_id) {

        Organization organization = getOrganization(organization_id);
        List<Event> events = organization.getEvents();

        List<Track> possibleTracks = new ArrayList<>();

        while( !events.isEmpty() ){
            Track track = new Track();
            List<Event> beforeMiddayEvents = scheduleBlockOfDuration(events, 180);
            List<Event> afterMiddayEvents = scheduleBlockOfDuration(events, 240);

            track.setBeforeMidDayEvents(beforeMiddayEvents);
            track.setAfterMidDayEvents(afterMiddayEvents);
            possibleTracks.add(track);
        }

        return possibleTracks;
    }

    private List<Event> scheduleBlockOfDuration(List<Event> events, int durationBlock) {
        List<Event> resEvents = new ArrayList<>();

        // TODO : refactorable code block
        while (true) {
            if (events.stream().filter(event -> event.getDuration() == durationBlock).count() > 0) {
                Optional<Event> eventOpt = events.stream().filter(event -> event.getDuration() == durationBlock).findAny();
                resEvents.add(eventOpt.get());
                events.remove(eventOpt.get());
            } else if (events.stream().filter(event -> event.getDuration() < durationBlock).count() > 0) {
                Optional<Event> eventOpt = events.stream().filter(event -> event.getDuration() < durationBlock).findAny();
                resEvents.add(eventOpt.get());
                events.remove(eventOpt.get());
            } else {
                // if there is no event that is suitable for the rest duration time, return the result list
                break;
            }
        }

        return resEvents;
    }

    private int getTotalEventTimeOfBlock(List<Event> eventBlock){
        return eventBlock.stream().map(Event::getDuration).mapToInt(Integer::intValue).sum();
    }

}
