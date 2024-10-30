package com.example.wallet.service;

import com.example.wallet.domain.OperationDto;
import com.example.wallet.domain.OperationType;
import com.example.wallet.domain.Wallet;
import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.WalletNotFoundException;
import com.example.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public void makeOperation(OperationDto operation) {
        UUID walletId = operation.walletId();
        Wallet wallet = walletRepository.findByIdWithLock(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        Double balance = wallet.getBalance();
        Double amount = operation.amount();

        if (operation.operationType().equals(OperationType.DEPOSIT)) {
            balance += amount;
        } else {
            if (balance < amount) {
                throw new InsufficientFundsException();
            }
            balance -= amount;
        }
        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }

    public Double getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId))
                .getBalance();
    }

}
