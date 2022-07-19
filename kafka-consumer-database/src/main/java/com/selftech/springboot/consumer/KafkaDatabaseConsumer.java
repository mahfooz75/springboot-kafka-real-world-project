package com.selftech.springboot.consumer;

import com.selftech.springboot.constant.KafkaConstant;
import com.selftech.springboot.entity.WikimediaData;
import com.selftech.springboot.repository.WikimediaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KafkaDatabaseConsumer {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:100}")
    private int batchSize;

    private final WikimediaDataRepository wikimediaDataRepository;
    private final List<WikimediaData> wikimediaDataList;

    public KafkaDatabaseConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
        this.wikimediaDataList = new ArrayList<>();
    }


    @KafkaListener(topics = KafkaConstant.WIKIMEDIA_RECENTCHANGE_TOPIC, groupId = "myGroup")
    public void consume(String eventMessage) {
        log.info("Event message received -> {}", eventMessage);

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        wikimediaDataList.add(wikimediaData);

        //wikimediaDataRepository.save(wikimediaData);
        if (wikimediaDataList.size() == batchSize) {
            wikimediaDataRepository.saveAll(wikimediaDataList);
            wikimediaDataList.clear();
        }

    }

}
