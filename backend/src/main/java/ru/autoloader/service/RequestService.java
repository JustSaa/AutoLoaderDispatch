package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autoloader.model.Request;
import ru.autoloader.model.RequestStatus;
import ru.autoloader.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;

    // Получить все заявки
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    // Найти заявку по ID
    public Optional<Request> getRequestById(Long id) {
        return requestRepository.findById(id);
    }

    // Создать новую заявку
    public Request createRequest(Request request) {
        request.setStatus(RequestStatus.NEW); // Новая заявка
        return requestRepository.save(request);
    }

    // Обновить статус заявки
    public Request updateRequestStatus(Long id, RequestStatus status) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            request.setStatus(status);
            return requestRepository.save(request);
        }
        throw new RuntimeException("Заявка не найдена!");
    }
}