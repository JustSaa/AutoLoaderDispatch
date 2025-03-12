package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autoloader.exception.RequestNotFoundException;
import ru.autoloader.model.*;
import ru.autoloader.repository.LoaderRepository;
import ru.autoloader.repository.RequestRepository;

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

        // Находим ближайшего свободного погрузчика
        Loader nearestLoader = findNearestAvailableLoader(request.getWarehouse());

        if (nearestLoader != null) {
            request.setLoader(nearestLoader);
            nearestLoader.setStatus(LoaderStatus.BUSY); // Обновляем статус погрузчика
            loaderRepository.save(nearestLoader); // Сохраняем изменения в БД
        }

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

    private Loader findNearestAvailableLoader(Warehouse warehouse) {
        List<Loader> availableLoaders = loaderRepository.findByStatus(LoaderStatus.IDLE);

        if (availableLoaders.isEmpty()) {
            return null; // Нет свободных погрузчиков
        }

        return availableLoaders.stream()
                .min(Comparator.comparingDouble(loader -> calculateDistance(
                        warehouse.getLatitude(), warehouse.getLongitude(),
                        loader.getLatitude(), loader.getLongitude())))
                .orElse(null);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Радиус Земли в метрах
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Расстояние в метрах
    }
}