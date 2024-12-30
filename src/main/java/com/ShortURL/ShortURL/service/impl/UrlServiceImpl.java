package com.ShortURL.ShortURL.service.impl;

import com.ShortURL.ShortURL.exception.NotFound;
import com.ShortURL.ShortURL.persistence.entity.Url;
import com.ShortURL.ShortURL.persistence.repository.UrlRepository;
import com.ShortURL.ShortURL.presentation.dto.PostUrlDTO;
import com.ShortURL.ShortURL.presentation.dto.UrlDTO;
import com.ShortURL.ShortURL.service.UrlService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

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
