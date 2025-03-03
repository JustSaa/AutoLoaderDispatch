package ru.autoloader.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loader_id")
    private Loader loader; // Назначенный погрузчик

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Кто сделал запрос

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse; // Где нужна погрузка

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status; // NEW, IN_PROGRESS, DONE, CANCELED

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}