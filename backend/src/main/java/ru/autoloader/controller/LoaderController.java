package ru.autoloader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.autoloader.model.Loader;
import ru.autoloader.service.LoaderService;

import java.util.List;

@RestController
@RequestMapping("/api/loaders")
@RequiredArgsConstructor
@Tag(name = "Loaders API", description = "Операции с погрузчиками")  // Группа в Swagger
public class LoaderController {

    private final LoaderService loaderService;

    @GetMapping
    @Operation(summary = "Получить всех погрузчиков", description = "Возвращает список всех погрузчиков")
    public List<Loader> getAllLoaders() {
        return loaderService.getAllLoaders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти погрузчик по ID", description = "Возвращает погрузчик по его ID")
    public ResponseEntity<Loader> getLoaderById(@PathVariable Long id) {
        Loader loader = loaderService.getLoaderById(id);
        return ResponseEntity.ok(loader);
    }

    @PostMapping
    @Operation(summary = "Создать погрузчик", description = "Добавляет нового погрузчика в систему")
    public Loader createLoader(@RequestBody Loader loader) {
        return loaderService.createLoader(loader);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить погрузчика", description = "Удаляет погрузчика по его ID")
    public ResponseEntity<Void> deleteLoader(@PathVariable Long id) {
        loaderService.deleteLoader(id);
        return ResponseEntity.noContent().build();
    }
}