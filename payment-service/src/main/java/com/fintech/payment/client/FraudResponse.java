package com.fintech.payment.client;

public record FraudResponse(boolean isFraud, double riskScore) {}

