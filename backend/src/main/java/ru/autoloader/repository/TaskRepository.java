package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
