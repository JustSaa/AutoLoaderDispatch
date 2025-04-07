package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.autoloader.model.Request;
import ru.autoloader.model.Task;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyOperator(Request request) {
        messagingTemplate.convertAndSend("/topic/requests", request);
    }

    public void notifyLoader(Task task) {
        messagingTemplate.convertAndSend("/topic/loaders/" + task.getLoader().getId(), task);
    }
}
