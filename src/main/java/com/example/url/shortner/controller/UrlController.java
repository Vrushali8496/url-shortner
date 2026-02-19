package com.example.url.shortner.controller;

import com.example.url.shortner.entity.Url;
import com.example.url.shortner.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {

        String originalUrl = request.get("url");
        String customCode = request.get("customCode");

        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "URL cannot be empty"));
        }

        try {
            String shortCode = service.createShortUrl(originalUrl, customCode);

            // ✅ Dynamic base URL (works in localhost & Render)
            String baseUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .build()
                    .toUriString();

            String fullShortUrl = baseUrl + "/r/" + shortCode;

            return ResponseEntity.ok(
                    Map.of("shortUrl", fullShortUrl)
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {

        try {
            Url url = service.getUrlByShortCode(shortCode);

            service.incrementClickCount(url);

            return ResponseEntity
                    .status(302)
                    .location(URI.create(url.getOriginalUrl()))
                    .build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<?> getStats(@PathVariable String code) {

        try {
            Url url = service.getUrlByShortCode(code);

            return ResponseEntity.ok(Map.of(
                    "originalUrl", url.getOriginalUrl(),
                    "clickCount", url.getClickCount()
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
