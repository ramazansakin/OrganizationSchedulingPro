package com.sakinramazan.micros.organizationschedulingpro.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Data
@Document(indexName = "organization_db", type = "event")
public class EventDocument {
    @Id
    private Integer id;
    private String subject;
    private Integer duration;
    private Integer organization;
}
