package com.fintech.payment.service;

import com.fintech.payment.client.FraudClient;
import com.fintech.payment.client.FraudRequest;
import com.fintech.payment.client.FraudResponse;
import com.fintech.payment.model.Transaction;
import com.fintech.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired; // Field injection is fine for MVP
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private FraudClient fraudClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Transaction processPayment(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("PENDING");

        // 1. Check Fraud
        // Ensure you are passing the double value
        FraudResponse fraudCheck = fraudClient.evaluateFraud(
                new FraudRequest(transaction.getUserId(), transaction.getAmount().doubleValue())
        );

        // Accessors for records are method calls like .riskScore(), not .getRiskScore()
        transaction.setFraudScore(fraudCheck.riskScore());

        if (fraudCheck.isFraud()) {
            transaction.setStatus("REJECTED");
            transaction.setRejectionReason("High Risk Score: " + fraudCheck.riskScore());
        } else {
            transaction.setStatus("APPROVED");
        }

        // 2. Save to DB
        // Because Repository is now typed <Transaction, Long>, this returns Transaction, not Object
        Transaction saved = repository.save(transaction);

        // 3. Kafka
        String eventMessage = "Transaction " + saved.getId() + " was " + saved.getStatus();
        kafkaTemplate.send("transaction-topic", eventMessage);

        return saved;
    }
}