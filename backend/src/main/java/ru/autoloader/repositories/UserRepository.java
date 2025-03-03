package ru.autoloader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
