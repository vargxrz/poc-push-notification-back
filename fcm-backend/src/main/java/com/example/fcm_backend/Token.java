package com.example.fcm_backend;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tokens")
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;
}
