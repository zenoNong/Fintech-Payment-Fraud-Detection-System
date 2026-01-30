package com.fintech.payment.controller;

import com.fintech.payment.model.Transaction;
import com.fintech.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/initiate")
    public Transaction initiatePayment(@RequestBody Transaction transaction) {
        return service.processPayment(transaction);
    }
}