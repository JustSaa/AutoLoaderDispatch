package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
