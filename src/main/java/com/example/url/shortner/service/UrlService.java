package com.example.url.shortner.service;

import com.example.url.shortner.entity.Url;
import com.example.url.shortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    // ✅ Updated method (supports custom code)
    public String createShortUrl(String originalUrl, String customCode) {

        String shortCode;

        // 1️⃣ If user entered custom code
        if (customCode != null && !customCode.isBlank()) {

            // Validate format (only letters, numbers, _ and - allowed)
            if (!customCode.matches("^[a-zA-Z0-9_-]+$")) {
                throw new RuntimeException("Invalid custom short code format!");
            }

            // Check if already exists
            if (repository.existsByShortCode(customCode)) {
                throw new RuntimeException("Custom short code already exists!");
            }

            shortCode = customCode;

        } else {
            // 2️⃣ Generate random short code
            shortCode = generateUniqueCode();
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setClickCount(0);  // initialize

        repository.save(url);

        return shortCode;
    }

    // ✅ Separate method to generate unique random code
    private String generateUniqueCode() {
        String shortCode;

        do {
            shortCode = UUID.randomUUID().toString().substring(0, 6);
        } while (repository.existsByShortCode(shortCode));

        return shortCode;
    }

    public Url getUrlByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode).orElse(null);
    }

    public void updateClickCount(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);
    }
}
