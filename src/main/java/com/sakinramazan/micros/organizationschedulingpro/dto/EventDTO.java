package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class EventDTO implements Serializable {

    private String subject;
    private String time;
    private String duration;
    private Integer trackno;

}
