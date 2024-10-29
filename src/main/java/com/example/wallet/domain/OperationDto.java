package com.example.wallet.domain;

import java.util.UUID;

public record OperationDto(UUID walletId, OperationType operationType, Double amount) {
}
