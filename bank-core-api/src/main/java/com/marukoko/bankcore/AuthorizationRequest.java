package com.marukoko.bankcore;

import java.math.BigDecimal;

public record AuthorizationRequest(
        BigDecimal amount,
        String merchantId
) {
}
