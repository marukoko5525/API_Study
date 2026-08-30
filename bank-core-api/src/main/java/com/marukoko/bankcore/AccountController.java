package com.marukoko.bankcore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AccountController {

    private static final Map<String, BigDecimal> ACCOUNT_BALANCES = new ConcurrentHashMap<>(Map.of(
            "1001", new BigDecimal("125000.00"),
            "1002", new BigDecimal("8900.50"),
            "1003", new BigDecimal("0.00")
    ));

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable String accountId) {
        BigDecimal balance = ACCOUNT_BALANCES.get(accountId);
        if (balance == null) {
            return ResponseEntity.notFound().build();
        }
        AccountBalanceResponse response = new AccountBalanceResponse(
                accountId, balance, "JPY", OffsetDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountId}/authorizations")
    public ResponseEntity<AuthorizationResponse> authorize(
            @PathVariable String accountId,
            @RequestBody AuthorizationRequest request) {
        BigDecimal balance = ACCOUNT_BALANCES.get(accountId);
        if (balance == null) {
            return ResponseEntity.notFound().build();
        }

        String authorizationId = UUID.randomUUID().toString();
        String status;
        if (balance.compareTo(request.amount()) < 0) {
            status = "DECLINED";
        } else {
            ACCOUNT_BALANCES.put(accountId, balance.subtract(request.amount()));
            status = "APPROVED";
        }

        AuthorizationResponse response = new AuthorizationResponse(
                authorizationId, accountId, request.amount(), status, OffsetDateTime.now());
        return ResponseEntity.ok(response);
    }
}
