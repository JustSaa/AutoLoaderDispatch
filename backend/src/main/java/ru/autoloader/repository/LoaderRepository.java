package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Loader;
import ru.autoloader.model.LoaderStatus;

import java.util.List;

public interface LoaderRepository extends JpaRepository<Loader, Long> {
    List<Loader> findByStatus(LoaderStatus status);
}