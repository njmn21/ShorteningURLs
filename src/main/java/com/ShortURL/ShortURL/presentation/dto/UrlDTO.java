package com.ShortURL.ShortURL.presentation.dto;

import java.time.LocalDateTime;

public record UrlDTO(
        Long id,
        String url,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
