package com.example.wallet.controller;

import com.example.wallet.WalletApplicationTests;
import com.example.wallet.domain.OperationDto;
import com.example.wallet.domain.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class WalletControllerTest extends WalletApplicationTests {

    @Test
    public void whenMakeDeposit_thenSuccess() {
        String url = "http://localhost:" + port + "/api/v1/wallet";
        OperationDto request = new OperationDto(presentWalletId, OperationType.DEPOSIT, 100D);

        ResponseEntity<String> actualResponse = restTemplate.postForEntity(url, request, String.class);

        Assertions.assertEquals(200, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Success!", actualResponse.getBody());
    }

    @Test
    public void whenMakeWithdraw_thenSuccess() {
        String url = "http://localhost:" + port + "/api/v1/wallet";
        OperationDto request = new OperationDto(presentWalletId, OperationType.WITHDRAW, 50D);

        ResponseEntity<String> actualResponse = restTemplate.postForEntity(url, request, String.class);

        Assertions.assertEquals(200, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Success!", actualResponse.getBody());
    }

    @Test
    public void whenMakeExceedingWithdraw_thenInsufficientFunds() {
        String url = "http://localhost:" + port + "/api/v1/wallet";
        OperationDto request = new OperationDto(presentWalletId, OperationType.WITHDRAW, 200D);

        ResponseEntity<String> actualResponse = restTemplate.postForEntity(url, request, String.class);

        Assertions.assertEquals(400, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Insufficient funds!", actualResponse.getBody());
    }

    @Test
    public void whenMakeOperationWithNotPresentWalletId_thenNotFound() {
        String url = "http://localhost:" + port + "/api/v1/wallet";
        UUID id = UUID.fromString("d0c5f731-c076-44d3-83a8-2f2304f00000");
        OperationDto request = new OperationDto(id, OperationType.WITHDRAW, 50D);

        ResponseEntity<String> actualResponse = restTemplate.postForEntity(url, request, String.class);

        Assertions.assertEquals(404, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Wallet " + id + " not found!", actualResponse.getBody());
    }

    @Test
    public void whenMakeOperationWithNegativeAmount_thenBadOperation() {
        String url = "http://localhost:" + port + "/api/v1/wallet";
        OperationDto request = new OperationDto(presentWalletId, OperationType.WITHDRAW, -50D);

        ResponseEntity<String> actualResponse = restTemplate.postForEntity(url, request, String.class);

        Assertions.assertEquals(400, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Bad operation!", actualResponse.getBody());
    }

    @Test
    public void whenGetBalance_thenReceiveBalance() {
        String url = "http://localhost:" + port + "/api/v1/wallet/" + presentWalletId;

        ResponseEntity<String> actualResponse = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(200, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Your balance: 100,00", actualResponse.getBody());
    }

    @Test
    public void whenGetBalanceWithNotPresentWalletId_thenNotFound() {
        UUID id = UUID.fromString("d0c5f731-c076-44d3-83a8-2f2304f00000");
        String url = "http://localhost:" + port + "/api/v1/wallet/" + id;

        ResponseEntity<String> actualResponse = restTemplate.getForEntity(url, String.class);

        Assertions.assertEquals(404, actualResponse.getStatusCode().value());
        Assertions.assertEquals("Wallet " + id + " not found!", actualResponse.getBody());
    }

}
