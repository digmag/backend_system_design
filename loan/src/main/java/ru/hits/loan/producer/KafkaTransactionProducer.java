package ru.hits.loan.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.TransactionMessageDTO;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@RequiredArgsConstructor
public class KafkaTransactionProducer {

    private final KafkaTemplate<String, TransactionMessageDTO> kafkaTemplate;

    @Value("${kafka.topic.transactions}")
    private String transactionTopic;

    public void sendTransaction(TransactionMessageDTO message) {
        kafkaTemplate.send(transactionTopic, message);
    }
}
