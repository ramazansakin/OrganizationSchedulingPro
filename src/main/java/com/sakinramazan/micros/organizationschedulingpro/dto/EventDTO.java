package com.sakinramazan.micros.organizationschedulingpro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

@Value
public class EventDTO implements Serializable {

    String subject;
    String time;
    String duration;
    Integer trackNo;

    // For proper deserilization via jackson
    public EventDTO(@JsonProperty("subject") String subject,
                    @JsonProperty("time") String time,
                    @JsonProperty("duration") String duration,
                    @JsonProperty("trackNo") Integer trackNo) {
        this.subject = subject;
        this.time = time;
        this.duration = duration;
        this.trackNo = trackNo;
    }
}
