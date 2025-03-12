package ru.autoloader.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @NotBlank(message = "Имя погрузчика не может быть пустым")
    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Статус не может быть null")
    private LoaderStatus status; // IDLE, BUSY, OFFLINE

    @Column(nullable = false)
    @DecimalMin(value = "-90.0", message = "Широта должна быть в пределах -90.0 до 90.0")
    @DecimalMax(value = "90.0", message = "Широта должна быть в пределах -90.0 до 90.0")
    private Double latitude; // Координаты погрузчика

    @Column(nullable = false)
    @DecimalMin(value = "-180.0", message = "Долгота должна быть в пределах -180.0 до 180.0")
    @DecimalMax(value = "180.0", message = "Долгота должна быть в пределах -180.0 до 180.0")
    private Double longitude;

    @Column(name = "last_completed_at")
    private LocalDateTime lastCompletedAt;
}