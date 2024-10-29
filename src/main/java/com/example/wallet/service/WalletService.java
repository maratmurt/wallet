package com.example.wallet.service;

import com.example.wallet.domain.OperationDto;
import com.example.wallet.domain.OperationType;
import com.example.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.wallet.repository.WalletRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void makeOperation(OperationDto operation) {
        UUID walletId = operation.walletId();
        Wallet wallet = walletRepository.findById(walletId).orElseGet(()->{
            Wallet newWallet = new Wallet();
            newWallet.setId(walletId);
            newWallet.setBalance(0D);
            return walletRepository.save(newWallet);
        });
        Double balance = wallet.getBalance();
        Double amount = operation.amount();

        if (operation.operationType().equals(OperationType.DEPOSIT)) {
            balance += amount;
        } else {
            balance -= amount;
        }
        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }

    public Double getBalance(UUID walletId) {
        return walletRepository.findById(walletId).orElseThrow().getBalance();
    }

}
