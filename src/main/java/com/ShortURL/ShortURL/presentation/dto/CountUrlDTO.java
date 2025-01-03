package com.ShortURL.ShortURL.presentation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CountUrlDTO(
        Long id,
        String url,
        String shortCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long count
) {
}
