package ru.autoloader.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.autoloader.model.Loader;
import ru.autoloader.service.LoaderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/loaders")
@RequiredArgsConstructor
@Tag(name = "Loaders API", description = "Операции с погрузчиками")  // Группа в Swagger
public class LoaderController {

    private final LoaderService loaderService;

    @GetMapping
    @Operation(summary = "Получить всех погрузчиков", description = "Возвращает список всех погрузчиков")
    public List<Loader> getAllLoaders() {
        log.info("Получен запрос на список всех погрузчиков");
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

    @PutMapping("/{id}")
    @Operation(summary = "Обновить погрузчика", description = "Обновляет погрузчика в БД")
    public ResponseEntity<Loader> updateLoader(@PathVariable Long id,
                                               @Valid @RequestBody Loader loader) {
        Loader updatedLoader = loaderService.updateLoader(id, loader);
        return ResponseEntity.ok(updatedLoader);
    }
}