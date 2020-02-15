package com.sakinramazan.micros.organizationschedulingpro.model;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track implements Serializable {

    List<Event> beforeMidDayEvents;
    List<Event> afterMidDayEvents;

    @Override
    public String toString() {
        StringBuilder track = new StringBuilder();

        Calendar c = Calendar.getInstance();
        LocalDateTime timeAM = LocalDateTime.of(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                9,
                0);

        LocalDateTime timePM = timeAM.plusHours(4);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        for (Event event :
                beforeMidDayEvents) {

            String currTime = timeAM.format(dtf);
            track.append(currTime).append("AM").append(" ");
            track.append(event.getSubject()).append(" ");
            track.append(event.getDuration()).append("min");
            track.append("\n");
            timeAM.plusMinutes(event.getDuration());
        }

        // TODO : hard coded parameters refactorable
        track.append("12:00PM Lunch");
        track.append("\n");

        for (Event event :
                afterMidDayEvents) {

            String currTime = timePM.format(dtf);
            track.append(currTime).append("PM").append(" ");
            track.append(event.getSubject()).append(" ");
            track.append(event.getDuration()).append("min");
            track.append("\n");
            timePM.plusMinutes(event.getDuration());
        }

        return track.toString();
    }
}
