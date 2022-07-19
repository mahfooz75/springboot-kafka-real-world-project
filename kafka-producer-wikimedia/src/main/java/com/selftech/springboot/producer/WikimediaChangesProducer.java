package com.selftech.springboot.producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.selftech.springboot.constant.KafkaConstant;
import com.selftech.springboot.handler.WikimediaChangesHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Slf4j
public class WikimediaChangesProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        // TO READ REAL TIME STREAM DATA WE USE EVENT SOURCE
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, KafkaConstant.WIKIMEDIA_RECENTCHANGE_TOPIC);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(KafkaConstant.WIKIMEDIA_STREAM_URL));
        EventSource eventSource = builder.build();
        eventSource.start();
        MINUTES.sleep(10);
    }
}
