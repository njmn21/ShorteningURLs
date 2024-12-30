package com.ShortURL.ShortURL.service;

import com.ShortURL.ShortURL.presentation.dto.PostUrlDTO;
import com.ShortURL.ShortURL.presentation.dto.UrlDTO;

import java.util.Optional;

public interface UrlService {

    UrlDTO saveUrl(PostUrlDTO url);
    Optional<UrlDTO> getUrlbyShortCode(String shortCode);
}
