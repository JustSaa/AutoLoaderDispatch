package ru.autoloader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
