package com.siduska.ehealthwallet.mesaging.producer;


import com.siduska.ehealthwallet.mesaging.event.ReimbursementStatusChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReimburseStatusChangedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendReimbursementStatusChangedEvent(ReimbursementStatusChanged event) {
        kafkaTemplate.send("reimbursement-status-changed", event);
        System.out.println("Kafka event sent: " + event);
    }
}