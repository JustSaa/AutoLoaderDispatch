package ru.autoloader.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loaders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoaderStatus status; // IDLE, BUSY, OFFLINE

    @Column(nullable = false)
    private Double latitude; // Координаты погрузчика

    @Column(nullable = false)
    private Double longitude;
}