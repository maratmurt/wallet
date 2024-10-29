package com.example.wallet.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record OperationDto(
        @NotNull UUID walletId,
        @NotNull OperationType operationType,
        @NotNull @Positive Double amount
) {
}
