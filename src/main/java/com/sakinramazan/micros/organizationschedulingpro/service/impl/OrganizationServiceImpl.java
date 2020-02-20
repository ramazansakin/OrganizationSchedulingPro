package com.sakinramazan.micros.organizationschedulingpro.service.impl;

import com.sakinramazan.micros.organizationschedulingpro.dao.OrganizationRepository;
import com.sakinramazan.micros.organizationschedulingpro.dto.EventDTO;
import com.sakinramazan.micros.organizationschedulingpro.dto.OrganizationProgram;
import com.sakinramazan.micros.organizationschedulingpro.dto.Track;
import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import com.sakinramazan.micros.organizationschedulingpro.entity.Organization;
import com.sakinramazan.micros.organizationschedulingpro.exception.ResourceNotFoundException;
import com.sakinramazan.micros.organizationschedulingpro.service.EventService;
import com.sakinramazan.micros.organizationschedulingpro.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sakinramazan.micros.organizationschedulingpro.service.EventService.NETWORKING_EVENT;

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
                () -> new ResourceNotFoundException("Organization Not found by id : " + id));
        return organization;
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Event addEventToOrganization(Integer id, Event event) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if (organization.isPresent()) {
            event.setOrganization(organization.get());
            Event currEvent = eventService.createEvent(event);
            List<Event> events = organization.get().getEvents();
            events.add(currEvent);
            organizationRepository.save(organization.get());

            return currEvent;
        }
        return null;
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
    public OrganizationProgram scheduleEvents(Integer organization_id) {

        Organization organization = getOrganization(organization_id);
        List<Event> events = organization.getEvents();

        List<Track> possibleTracks = new ArrayList<>();

        while (!events.isEmpty()) {
            Track track = new Track();
            List<Event> beforeMiddayEvents = scheduleBlockOfDuration(events, 180);
            List<Event> afterMiddayEvents = scheduleBlockOfDuration(events, 240);

            // TODO - Networking Event may be seperated from Interface
            // if time condition is suitable, add Networking Event to end of the last presentation
            if (!afterMiddayEvents.isEmpty() && getTotalEventTimeOfBlock(afterMiddayEvents) > 180) {
                Event event = new Event();
                event.setSubject(NETWORKING_EVENT);
                event.setOrganization(organization);
                event.setDuration(0);
                // Not need to persist Networking events
                afterMiddayEvents.add(event);
            }

            track.setBeforeMidDayEvents(beforeMiddayEvents);
            track.setAfterMidDayEvents(afterMiddayEvents);
            possibleTracks.add(track);
        }

        return mapTrackListToDTOs(possibleTracks);
    }

    private OrganizationProgram mapTrackListToDTOs(List<Track> possibleTracks) {
        OrganizationProgram response = new OrganizationProgram();
        List<EventDTO> eventDTOS = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        // number of possible tracks
        int trackNumber = 1;
        for (Track track : possibleTracks) {
            // LocalDateTime initializations for each track
            LocalDateTime timeAM = LocalDateTime.of(
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH),
                    9,
                    0);

            LocalDateTime timePM = timeAM.plusHours(4);

            for (Event event : track.getBeforeMidDayEvents()) {
                StringBuilder presentationTime = new StringBuilder();
                presentationTime.append(timeAM.format(dtf)).append(" AM");
                EventDTO dto = new EventDTO(event.getSubject(), presentationTime.toString(), event.getDuration() + " minutes", trackNumber);
                timeAM = timeAM.plusMinutes(event.getDuration());
                eventDTOS.add(dto);
            }
            EventDTO lunchEvent = new EventDTO("Lunch", "12:00 PM", "", trackNumber);
            eventDTOS.add(lunchEvent);
            for (Event event : track.getAfterMidDayEvents()) {
                StringBuilder presentationTime = new StringBuilder();
                presentationTime.append(timePM.format(dtf)).append(" PM");
                EventDTO dto = new EventDTO(event.getSubject(),
                        presentationTime.toString(),
                        (event.getDuration() == 0 ? "" : event.getDuration() + " minutes"),
                        trackNumber);
                timePM = timePM.plusMinutes(event.getDuration());
                eventDTOS.add(dto);
            }
            ++trackNumber;
        }
        response.setTrackTables(eventDTOS);
        return response;
    }

    private List<Event> scheduleBlockOfDuration(List<Event> events, int durationBlock) {
        List<Event> resEvents = new ArrayList<>();

        // while keep it going till event list finishes
        while (true) {
            if (getIfAnyEvent(events, durationBlock) != null) {
                Event eventOpt = getIfAnyEvent(events, durationBlock);
                resEvents.add(eventOpt);
                durationBlock -= eventOpt.getDuration();
                events.remove(eventOpt);
            } else {
                // if there is no event that is suitable for the rest duration time, return the result list
                break;
            }
        }

        return resEvents;
    }

    private Event getIfAnyEvent(List<Event> events, final int duration) {
        if (!events.isEmpty()) {
            // If there is equal amout of duration block, get it first for fitting time table
            // or else get less value if possible
            if (events.stream().anyMatch(event -> event.getDuration() == duration))
                return events.stream().filter(event -> event.getDuration() == duration).collect(Collectors.toList()).get(0);
            else if (events.stream().anyMatch(event -> event.getDuration() < duration))
                return events.stream().filter(event -> event.getDuration() < duration).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    private int getTotalEventTimeOfBlock(List<Event> eventBlock) {
        return eventBlock.stream().map(Event::getDuration).mapToInt(Integer::intValue).sum();
    }

}
