package com.example.url.shortner.repository;

import com.example.url.shortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortCode(String shortCode);

    // ✅ Add this method
    boolean existsByShortCode(String shortCode);
}
