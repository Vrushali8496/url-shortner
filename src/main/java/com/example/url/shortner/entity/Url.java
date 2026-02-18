package com.example.url.shortner.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    private Integer clickCount = 0;
}
