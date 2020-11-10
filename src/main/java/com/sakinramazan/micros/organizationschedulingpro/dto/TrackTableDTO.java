package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class TrackTableDTO implements Serializable {

    private List<EventDTO> eventList;

    public TrackTableDTO() {
        eventList = new ArrayList<>();
    }
}

