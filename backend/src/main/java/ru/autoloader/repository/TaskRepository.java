package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByLoaderId(Long loaderId);
}
