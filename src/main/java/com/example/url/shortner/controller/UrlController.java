package com.example.url.shortner.controller;

import com.example.url.shortner.entity.Url;
import com.example.url.shortner.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String customCode = request.get("customCode");   // ✅ new

        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "URL cannot be empty"));
        }

        try {
            // ✅ Pass customCode to service
            String shortCode = service.createShortUrl(originalUrl, customCode);

            return ResponseEntity.ok(
                    Map.of("shortUrl", "http://localhost:8080/r/" + shortCode)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {

        Url url = service.getUrlByShortCode(shortCode);

        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        service.updateClickCount(url);

        return ResponseEntity
                .status(302)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }

    @GetMapping("/stats/{code}")
    public ResponseEntity<?> getStats(@PathVariable String code) {

        Url url = service.getUrlByShortCode(code);

        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of(
                "originalUrl", url.getOriginalUrl(),
                "clickCount", url.getClickCount()
        ));
    }
}
