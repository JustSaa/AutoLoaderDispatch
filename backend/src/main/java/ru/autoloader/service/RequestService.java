package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autoloader.exception.RequestNotFoundException;
import ru.autoloader.model.*;
import ru.autoloader.repository.LoaderRepository;
import ru.autoloader.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final LoaderRepository loaderRepository;

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
        request.setStatus(RequestStatus.NEW);

        // Находим наиболее подходящего свободного погрузчика
        Loader bestLoader = findBestAvailableLoader(request.getWarehouse());

        if (bestLoader != null) {
            request.setLoader(bestLoader);
            bestLoader.setStatus(LoaderStatus.BUSY); // Обновляем статус погрузчика
            loaderRepository.save(bestLoader); // Сохраняем изменения в БД
        }

        return requestRepository.save(request);
    }

    // Обновление статуса заявки
    public Request updateRequestStatus(Long id, RequestStatus status) {
        log.info("Обновление статуса заявки ID {} -> {}", id, status);
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка! Заявка с ID {} не найдена", id);
                    return new RequestNotFoundException("Request not found with id: " + id);
                });

        request.setStatus(status);

        // Если заявка завершена - обновляем время у погрузчика
        if (status == RequestStatus.DONE && request.getLoader() != null) {
            Loader loader = request.getLoader();
            loader.setLastCompletedAt(LocalDateTime.now());
            loader.setStatus(LoaderStatus.IDLE); // Освобождаем погрузчик
            loaderRepository.save(loader);
        }

        return requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        log.info("Удаление заявки ID: {}", id);
        if (!requestRepository.existsById(id)) {
            log.warn("Попытка удалить несуществующую заявку ID: {}", id);
            throw new RequestNotFoundException("Request with id " + id + " not found");
        }
        requestRepository.deleteById(id);
    }

    private Loader findBestAvailableLoader(Warehouse warehouse) {
        return loaderRepository.findAll().stream()
                .filter(loader -> loader.getStatus() == LoaderStatus.IDLE) // Только свободные
                .sorted(Comparator
                        .comparing((Loader l) -> getDistance(l, warehouse)) // 1. Сортировка по расстоянию
                        .thenComparing(l -> l.getLastCompletedAt() != null ? l.getLastCompletedAt() : LocalDateTime.MIN) // 2. По времени завершения
                )
                .findFirst()
                .orElse(null);
    }

    private double getDistance(Loader loader, Warehouse warehouse) {
        double dx = loader.getLatitude() - warehouse.getLatitude();
        double dy = loader.getLongitude() - warehouse.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }
}