package com.ShortURL.ShortURL.presentation.controller;

import com.ShortURL.ShortURL.presentation.dto.PostUrlDTO;
import com.ShortURL.ShortURL.presentation.dto.UrlDTO;
import com.ShortURL.ShortURL.service.UrlService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlDTO> saveUrl(@RequestBody PostUrlDTO url) {
        return ResponseEntity.ok(urlService.saveUrl(url));
    }

    @GetMapping
    public ResponseEntity<UrlDTO> getUrlbyShortCode(@RequestParam String shortCode) {
        return urlService.getUrlbyShortCode(shortCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<UrlDTO> updateUrl(@PathVariable String shortCode, @RequestBody PostUrlDTO url) {
        return ResponseEntity.ok(urlService.updateUrl(shortCode, url));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }
}
