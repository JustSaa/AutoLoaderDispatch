package ru.autoloader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.autoloader.model.Warehouse;
import ru.autoloader.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> list() {
        return warehouseService.getAllWarehouses();
    }
}
