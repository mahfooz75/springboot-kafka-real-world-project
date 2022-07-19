package com.selftech.springboot.consumer;

import com.selftech.springboot.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaDatabaseConsumer {

    @Value("${spring.kafka.consumer.group-id:myGroup}")
    private String consumerGroupId;

    @KafkaListener(topics = KafkaConstant.WIKIMEDIA_RECENTCHANGE_TOPIC, groupId = "myGroup")
    public void consume(String eventMessage) {
        log.info("Event message received -> {}", eventMessage);
    }

}
