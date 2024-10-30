package com.example.wallet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Wallet {

    @Id
    private UUID id;

    private Double balance;

}
