package ru.autoloader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.autoloader.model.Request;
import ru.autoloader.model.RequestStatus;
import ru.autoloader.service.RequestService;

import java.util.List;
import java.util.Optional;


@Slf4j
@Tag(name = "Requests", description = "API для управления заявками")
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @Operation(summary = "Получить все заявки", description = "Возвращает список всех заявок")
    @GetMapping
    public List<Request> getAllRequests() {
        log.info("Получен запрос на список всех заявок");
        return requestService.getAllRequests();
    }

    @Operation(summary = "Получить заявку по ID", description = "Возвращает заявку по указанному ID")
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        log.info("Получен запрос на получение заявки с ID: {}", id);
        Request request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @Operation(summary = "Создать новую заявку", description = "Создаёт новую заявку в системе")
    @PostMapping
    public ResponseEntity<Request> createRequest(@Valid @RequestBody Request request) {
        log.info("Получен запрос на создание новой заявки: {}", request);
        Request savedRequest = requestService.createRequest(request);
        return ResponseEntity.status(201).body(savedRequest);
    }

    @Operation(summary = "Обновить статус заявки", description = "Изменяет статус заявки")
    @PutMapping("/{id}/status")
    public Request updateRequestStatus(@PathVariable Long id, @RequestParam RequestStatus status) {
        log.info("Получен запрос на обновление статуса заявки ID {} -> {}", id, status);
        return requestService.updateRequestStatus(id, status);
    }

    @Operation(summary = "Удалить заявку", description = "Удаляет заявку по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.info("Получен запрос на удаление заявки ID: {}", id);
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}