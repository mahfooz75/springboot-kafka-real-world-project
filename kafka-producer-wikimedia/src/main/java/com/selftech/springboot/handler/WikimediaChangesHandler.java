package com.selftech.springboot.handler;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class WikimediaChangesHandler implements EventHandler {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {
        try {
            log.info("Opening");
        } catch (Exception e) {
            throw new Exception("Exception occurred while opening::" + e.getMessage());
        }
    }

    @Override
    public void onClosed() throws Exception {
        try {
            log.info("Closing");
        } catch (Exception e) {
            throw new Exception("Exception occurred while closing::" + e.getMessage());
        }
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        try {
            log.info(String.format("Event data -> %s", messageEvent.getData()));
            kafkaTemplate.send(topic, messageEvent.getData());
        } catch (Exception e) {
            throw new Exception("Exception occurred while processing message::" + e.getMessage());
        }
    }

    @Override
    public void onComment(String s) throws Exception {
        try {
            log.info("comment:: {}", s);
        } catch (Exception e) {
            throw new Exception("Exception occurred on comment::" + e.getMessage());
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
