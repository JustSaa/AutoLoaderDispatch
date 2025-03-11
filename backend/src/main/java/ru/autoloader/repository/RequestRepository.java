package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
