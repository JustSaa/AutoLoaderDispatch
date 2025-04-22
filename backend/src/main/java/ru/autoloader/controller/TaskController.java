package ru.autoloader.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.autoloader.model.Task;
import ru.autoloader.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // Задачи конкретного погрузчика
    @GetMapping("/loader/{loaderId}")
    public List<Task> byLoader(@PathVariable Long loaderId) {
        return taskService.getTasksForLoader(loaderId);
    }
}
