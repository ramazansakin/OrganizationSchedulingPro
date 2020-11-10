package com.sakinramazan.micros.organizationschedulingpro.dao;

import com.sakinramazan.micros.organizationschedulingpro.entity.EventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;

public interface EventElasticRepository extends ElasticsearchCrudRepository<EventDocument, Long> {

    List<EventDocument> getAllByOrganization(Integer organization);

}
