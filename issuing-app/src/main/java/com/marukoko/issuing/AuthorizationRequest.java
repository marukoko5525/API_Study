package com.marukoko.issuing;

import java.math.BigDecimal;

public record AuthorizationRequest(
        BigDecimal amount,
        String merchantId
) {
}
