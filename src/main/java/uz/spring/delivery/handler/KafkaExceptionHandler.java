package uz.spring.delivery.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaExceptionHandler implements CommonErrorHandler {

    private final DeadLetterPublishingRecoverer recoverer;

    public KafkaExceptionHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, ex) -> new org.apache.kafka.common.TopicPartition(record.topic() + "-DLT", record.partition()));
    }

    @Override
    public boolean handleOne(Exception ex,
                             ConsumerRecord<?, ?> record,
                             Consumer<?, ?> consumer,
                             MessageListenerContainer container) {
        log.error("Ошибка при обработке сообщения из топика {}: {}", record.topic(), ex.getMessage());

        recoverer.accept(record, ex);

        return true;
    }

    @Override
    public void handleOtherException(Exception ex,
                                     Consumer<?, ?> consumer,
                                     MessageListenerContainer container,
                                     boolean batchListener) {
        log.error("Глобальная ошибка Kafka Listener: {}", ex.getMessage());
    }
}
