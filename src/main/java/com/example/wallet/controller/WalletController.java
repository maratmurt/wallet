package com.example.wallet.controller;

import com.example.wallet.domain.OperationDto;
import com.example.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public ResponseEntity<String> operation(@RequestBody OperationDto operation) {
        walletService.makeOperation(operation);
        return ResponseEntity.ok("Success!");
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<String> balance(@PathVariable UUID id) {
        return ResponseEntity.ok("Your balance: " + walletService.getBalance(id));
    }

}
