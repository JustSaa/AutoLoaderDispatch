package ru.autoloader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.entities.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
