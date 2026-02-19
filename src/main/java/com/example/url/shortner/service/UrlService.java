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

    public String createShortUrl(String originalUrl, String customCode) {

        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("Original URL cannot be empty.");
        }

        originalUrl = originalUrl.trim();

        String shortCode;

        // ✅ If user entered custom code
        if (customCode != null && !customCode.isBlank()) {

            customCode = customCode.trim();

            // Validate format
            if (!customCode.matches("^[a-zA-Z0-9_-]+$")) {
                throw new IllegalArgumentException(
                        "Custom short code can only contain letters, numbers, _ and -"
                );
            }

            // Check duplicate
            if (repository.existsByShortCode(customCode)) {
                throw new IllegalArgumentException("Custom short code already exists.");
            }

            shortCode = customCode;

        } else {
            // ✅ Generate random unique code
            shortCode = generateUniqueCode();
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setClickCount(0);

        repository.save(url);

        return shortCode;
    }

    // ✅ Generates 6 character unique code
    private String generateUniqueCode() {

        String shortCode;

        do {
            shortCode = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 6);
        } while (repository.existsByShortCode(shortCode));

        return shortCode;
    }

    public Url getUrlByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("Short URL not found."));
    }

    public void incrementClickCount(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        repository.save(url);
    }
}
