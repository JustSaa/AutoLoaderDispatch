package ru.autoloader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.autoloader.model.Loader;
import ru.autoloader.service.LoaderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loaders")
@RequiredArgsConstructor
public class LoaderController {

    private final LoaderService loaderService;

    // Получить всех погрузчиков
    @GetMapping
    public List<Loader> getAllLoaders() {
        return loaderService.getAllLoaders();
    }

    // Найти погрузчик по ID
    @GetMapping("/{id}")
    public ResponseEntity<Loader> getLoaderById(@PathVariable Long id) {
        Optional<Loader> loader = loaderService.getLoaderById(id);
        return loader.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Добавить нового погрузчика
    @PostMapping
    public Loader createLoader(@RequestBody Loader loader) {
        return loaderService.createLoader(loader);
    }

    // Удалить погрузчика
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoader(@PathVariable Long id) {
        loaderService.deleteLoader(id);
        return ResponseEntity.noContent().build();
    }
}