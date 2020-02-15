package com.sakinramazan.micros.organizationschedulingpro.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationProgram {

    private String organizationName;
    private List<Track> tracks;

}
