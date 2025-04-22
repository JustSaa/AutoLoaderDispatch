package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.autoloader.model.Task;
import ru.autoloader.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasksForLoader(Long loaderId) {
        return taskRepository.findByLoaderId(loaderId);
    }
}
