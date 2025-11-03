package com.siduska.ehealthwallet.mesaging.consumer;

import com.siduska.ehealthwallet.mesaging.event.ReimbursementStatusChanged;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class ReimbursementStatusChangedConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    public ReimbursementStatusChangedConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @KafkaListener(topics = "reimbursement-status-changed", groupId = "reimbursement-service")
    public void onReimbursementStatusChanged(ReimbursementStatusChanged event) {
        System.out.println("Received Kafka event: " + event);

        //
        messagingTemplate.convertAndSend("/topic/reimbursement-updates", event);
    }
}