package com.marukoko.issuing;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class IssuingAccountController {

    private final RestClient bankCoreApiClient;

    public IssuingAccountController(RestClient bankCoreApiClient) {
        this.bankCoreApiClient = bankCoreApiClient;
    }

    @GetMapping("/issuing/accounts/{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getBalance(@PathVariable String accountId) {
        return bankCoreApiClient.get()
                .uri("/accounts/{accountId}/balance", accountId)
                .exchange((request, response) -> {
                    if (response.getStatusCode().equals(HttpStatusCode.valueOf(404))) {
                        return ResponseEntity.notFound().build();
                    }
                    AccountBalanceResponse body = response.bodyTo(AccountBalanceResponse.class);
                    return ResponseEntity.status(response.getStatusCode()).body(body);
                });
    }

    @PostMapping("/issuing/accounts/{accountId}/authorizations")
    public ResponseEntity<AuthorizationResponse> authorize(
            @PathVariable String accountId,
            @RequestBody AuthorizationRequest request) {
        return bankCoreApiClient.post()
                .uri("/accounts/{accountId}/authorizations", accountId)
                .body(request)
                .exchange((req, response) -> {
                    if (response.getStatusCode().equals(HttpStatusCode.valueOf(404))) {
                        return ResponseEntity.notFound().build();
                    }
                    AuthorizationResponse body = response.bodyTo(AuthorizationResponse.class);
                    return ResponseEntity.status(response.getStatusCode()).body(body);
                });
    }
}
