package com.ShortURL.ShortURL.service.impl;

import com.ShortURL.ShortURL.exception.NotFound;
import com.ShortURL.ShortURL.persistence.entity.Url;
import com.ShortURL.ShortURL.persistence.repository.UrlRepository;
import com.ShortURL.ShortURL.presentation.dto.CountUrlDTO;
import com.ShortURL.ShortURL.presentation.dto.PostUrlDTO;
import com.ShortURL.ShortURL.presentation.dto.UrlDTO;
import com.ShortURL.ShortURL.service.UrlService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    HashMap<String, Long> count = new HashMap<>();

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlDTO saveUrl(PostUrlDTO url) {
        String shortCode = generateShortCode();
        LocalDateTime currentTime = getCurrentTime();
        LocalDateTime updatedTime = getCurrentTime();

        var urlEntity = Url.builder()
                .url(url.url())
                .shortCode(shortCode)
                .createdAt(currentTime)
                .updatedAt(updatedTime)
                .build();

        urlRepository.save(urlEntity);

        return new UrlDTO(
                urlEntity.getId(),
                urlEntity.getUrl(),
                urlEntity.getShortCode(),
                urlEntity.getCreatedAt(),
                urlEntity.getUpdatedAt()
        );
    }

    @Override
    public Optional<UrlDTO> getUrlbyShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(urlEntity -> new UrlDTO(
                        urlEntity.getId(),
                        urlEntity.getUrl(),
                        urlEntity.getShortCode(),
                        urlEntity.getCreatedAt(),
                        urlEntity.getUpdatedAt()
                ))
                .or(() -> {
                    throw new NotFound("Short code not found");
                });
    }

    @Override
    public UrlDTO updateUrl(String shortCode, PostUrlDTO url) {
        LocalDateTime updatedTime = getCurrentTime();

        var urlEntity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFound("Short code not found"));

        urlEntity.setUrl(url.url());
        urlEntity.setUpdatedAt(updatedTime);

        urlRepository.save(urlEntity);

        return new UrlDTO(
                urlEntity.getId(),
                urlEntity.getUrl(),
                urlEntity.getShortCode(),
                urlEntity.getCreatedAt(),
                urlEntity.getUpdatedAt()
        );
    }

    @Override
    public void deleteUrl(String shortCode) {
//        var urlEntity = urlRepository.findByShortCode(shortCode)
//                .orElseThrow(() -> new NotFound("Short code not found"));
//
//        Long deleted = urlRepository.deleteByShortCode(shortCode);
//
//        if (deleted == 0) {
//            throw new NotFound("Short code not found");
//        }
        Optional<Url> urlEntity = urlRepository.findByShortCode(shortCode);

        if (urlEntity.isPresent()) {
            urlRepository.deleteByShortCode(shortCode);
        } else {
            throw new RuntimeException("Short code not found");
        }
    }

    @Override
    public Optional<CountUrlDTO> countUrl(String shortCode) {

        if (count.containsKey(shortCode)) {
            count.put(shortCode, count.get(shortCode) + 1);
        } else {
            count.put(shortCode, 1L);
        }

        return urlRepository.findByShortCode(shortCode)
                .map(urlEntity -> new CountUrlDTO(
                        urlEntity.getId(),
                        urlEntity.getUrl(),
                        urlEntity.getShortCode(),
                        urlEntity.getCreatedAt(),
                        urlEntity.getUpdatedAt(),
                        count.get(shortCode)
                ))
                .or(() -> {
                    throw new NotFound("Short code not found");
                });
    }

    private String generateShortCode() {
        HashSet<String> shortCodes = new HashSet<>();
        Random random = new Random();
        StringBuilder result;

        do {
            result = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                char randomLetter = (char) ('A' + random.nextInt(26));
                result.append(randomLetter);
            }

            for (int i = 0; i < 3; i++) {
                int randomNumber = random.nextInt(10);
                result.append(randomNumber);
            }
        } while (!shortCodes.add(result.toString()));

        return result.toString();
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
