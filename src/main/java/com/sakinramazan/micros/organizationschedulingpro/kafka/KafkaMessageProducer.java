package com.sakinramazan.micros.organizationschedulingpro.kafka;

import com.sakinramazan.micros.organizationschedulingpro.entity.EventDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> template;

    @Value("${tpd.topic-name}")
    private String topicName;

    @Value("${tpd.messages-per-request}")
    private int messagesPerRequest;

    public void simpleStringMesgProduce(String sampleMsg) {
        template.send(topicName, sampleMsg);
    }

    public void produceEventDocument(EventDocument sampleEvent) {
        template.send(topicName, sampleEvent);
    }

}
