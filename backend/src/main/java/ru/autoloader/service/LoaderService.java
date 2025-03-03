package ru.autoloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.autoloader.model.Loader;
import ru.autoloader.repository.LoaderRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoaderService {

    private final LoaderRepository loaderRepository;

    // Получить всех погрузчиков
    public List<Loader> getAllLoaders() {
        return loaderRepository.findAll();
    }

    // Найти погрузчик по ID
    public Optional<Loader> getLoaderById(Long id) {
        return loaderRepository.findById(id);
    }

    // Добавить нового погрузчика
    public Loader createLoader(Loader loader) {
        return loaderRepository.save(loader);
    }

    // Удалить погрузчика
    public void deleteLoader(Long id) {
        loaderRepository.deleteById(id);
    }
}