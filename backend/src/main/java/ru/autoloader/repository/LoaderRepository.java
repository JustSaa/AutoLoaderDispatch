package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Loader;

public interface LoaderRepository extends JpaRepository<Loader, Long> {
}