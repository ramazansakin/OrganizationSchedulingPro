package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrganizationProgram implements Serializable {

//    private String organizationName;
    private List<EventDTO> trackTables;

    public OrganizationProgram() {
        trackTables = new ArrayList<>();
    }
}
