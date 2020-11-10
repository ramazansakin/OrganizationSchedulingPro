package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrganizationProgram implements Serializable {

//    private String organizationName;
    private List<EventDTO> trackTables;

    public OrganizationProgram() {
        trackTables = new ArrayList<>();
    }
}
