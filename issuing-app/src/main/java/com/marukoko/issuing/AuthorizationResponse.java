package com.marukoko.issuing;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AuthorizationResponse(
        String authorizationId,
        String accountId,
        BigDecimal amount,
        String status,
        OffsetDateTime authorizedAt
) {
}
