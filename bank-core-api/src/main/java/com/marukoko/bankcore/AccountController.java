package com.marukoko.bankcore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class AccountController {

    private static final Map<String, BigDecimal> MOCK_BALANCES = Map.of(
            "1001", new BigDecimal("125000.00"),
            "1002", new BigDecimal("8900.50"),
            "1003", new BigDecimal("0.00")
    );

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable String accountId) {
        BigDecimal balance = MOCK_BALANCES.get(accountId);
        if (balance == null) {
            return ResponseEntity.notFound().build();
        }
        AccountBalanceResponse response = new AccountBalanceResponse(
                accountId, balance, "JPY", OffsetDateTime.now());
        return ResponseEntity.ok(response);
    }
}
