package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.RefreshToken;
import ru.autoloader.model.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}