package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autoloader.exception.RequestNotFoundException;
import ru.autoloader.model.Request;
import ru.autoloader.model.RequestStatus;
import ru.autoloader.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;

    // Получить все заявки
    public List<Request> getAllRequests() {
        log.info("Получение всех заявок");
        return requestRepository.findAll();
    }

    // Найти заявку по ID
    public Request getRequestById(Long id) {
        log.info("Поиск заявки по ID: {}", id);
        return requestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Заявка с ID {} не найдена!", id);
                    return new RequestNotFoundException("Request with id " + id + " not found");
                });
    }

    // Создать новую заявку
    public Request createRequest(Request request) {
        log.info("Создание новой заявки: {}", request);
        request.setStatus(RequestStatus.NEW); // Новая заявка
        return requestRepository.save(request);
    }

    // Обновить статус заявки
    public Request updateRequestStatus(Long id, RequestStatus status) {
        log.info("Обновление статуса заявки ID {} -> {}", id, status);
        return requestRepository.findById(id)
                .map(request -> {
                    request.setStatus(status);
                    return requestRepository.save(request);
                })
                .orElseThrow(() -> {
                    log.error("Ошибка! Заявка с ID {} не найдена", id);
                    return new RequestNotFoundException("Request with id " + id + " not found");
                });
    }

    public void deleteRequest(Long id) {
        log.info("Удаление заявки ID: {}", id);
        if (!requestRepository.existsById(id)) {
            log.warn("Попытка удалить несуществующую заявку ID: {}", id);
            throw new RequestNotFoundException("Request with id " + id + " not found");
        }
        requestRepository.deleteById(id);
    }
}