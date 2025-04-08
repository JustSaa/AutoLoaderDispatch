package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Loader;
import ru.autoloader.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByLoaderAndAssignedAtAfter(Loader loader, LocalDateTime time);
    Optional<Task> findFirstByLoaderAndAssignedAtAfterOrderByAssignedAtDesc(Loader loader, LocalDateTime time);
}
