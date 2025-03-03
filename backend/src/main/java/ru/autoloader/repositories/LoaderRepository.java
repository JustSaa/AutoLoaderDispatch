package ru.autoloader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.entities.Loader;

public interface LoaderRepository extends JpaRepository<Loader, Long> {
}