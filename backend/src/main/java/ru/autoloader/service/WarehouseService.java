package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.autoloader.model.Warehouse;
import ru.autoloader.repository.WarehouseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository repo;

    public List<Warehouse> getAllWarehouses() {
        return repo.findAll();
    }
}
