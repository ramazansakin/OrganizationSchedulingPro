package com.sakinramazan.micros.organizationschedulingpro.model;

import com.sakinramazan.micros.organizationschedulingpro.entity.Event;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Track implements Serializable {

    List<Event> beforeMidDayEvents;
    List<Event> afterMidDayEvents;

}
