package com.ShortURL.ShortURL.presentation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UrlDTO(
        Long id,
        String url,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
