package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public final class EventDTO implements Serializable {

    private final String subject;
    private final String time;
    private final String duration;

}
