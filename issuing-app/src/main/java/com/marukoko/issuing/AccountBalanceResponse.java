package com.marukoko.issuing;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountBalanceResponse(
        String accountId,
        BigDecimal balance,
        String currency,
        OffsetDateTime updatedAt
) {
}
