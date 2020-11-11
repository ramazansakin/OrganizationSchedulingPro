package com.sakinramazan.micros.organizationschedulingpro.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, Object> template;

    @Value("${tpd.topic-name}")
    private String topicName;

    @Value("${tpd.messages-per-request}")
    private int messagesPerRequest;

    void simpleStringMesgProduce(String sampleMsg) {
        template.send(topicName, sampleMsg);
    }

}
