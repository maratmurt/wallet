package com.example.wallet.controller;

import com.example.wallet.domain.OperationDto;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    private final DecimalFormat df = new DecimalFormat("#.00");

    @PostMapping("/wallet")
    public ResponseEntity<String> operation(@Valid @RequestBody OperationDto operation) {
        walletService.makeOperation(operation);
        return ResponseEntity.ok("Success!");
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<String> balance(@PathVariable UUID id) {
        Double balance = walletService.getBalance(id);
        return ResponseEntity.ok("Your balance: " + df.format(balance));
    }

}
