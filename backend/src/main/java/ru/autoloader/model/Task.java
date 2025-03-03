package ru.autoloader.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loader_id", nullable = false)
    private Loader loader; // Какой погрузчик выполняет

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse; // Где выполняется

    @Column(nullable = false)
    private String description; // Описание работы

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();
}