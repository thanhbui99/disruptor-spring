package vn.com.disruptorspring.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import vn.com.disruptorspring.cache.ResponseCache;
import vn.com.disruptorspring.model.response.ResponseDto;

@Component
public class ConsumerResponseOrder {
    public static final String TOPIC_RESULT_ORDER = "TOPIC-RESULT-ORDER";

    @Autowired
    private ResponseCache responseCache;

    @KafkaListener(topics = TOPIC_RESULT_ORDER, groupId = "thanhbn", containerFactory = "kafkaListenerContainerFactoryLocal")
    public void listen(ConsumerRecord<String, ResponseDto> consumerRecord, Acknowledgment ack) {
        try {
            responseCache.put(consumerRecord.value());
        } finally {
            ack.acknowledge();
        }
    }
}
