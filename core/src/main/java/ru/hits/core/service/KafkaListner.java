package ru.hits.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.bill.TransactionCreateDTO;
import ru.hits.common.dtos.bill.TransactionMessageDTO;
import ru.hits.common.dtos.loan.NotificationDTO;
import ru.hits.core.service.interfaces.IIntegrationBillService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaListner {

    private final IIntegrationBillService billService;

    @KafkaListener(topics = "transaction", groupId = "transaction_group")
    public void transactionNotification(TransactionMessageDTO notificationDTO, UUID ik){
        TransactionCreateDTO createDTO = new TransactionCreateDTO(
                notificationDTO.getAmount()
        );
        System.out.println("транзакция"+notificationDTO.getDealId().toString() + " "+ notificationDTO.getBillId().toString() + " " +
                notificationDTO.getAmount());
        billService.transaction(notificationDTO.getDealId(), notificationDTO.getBillId(), createDTO, ik);
    }
}
