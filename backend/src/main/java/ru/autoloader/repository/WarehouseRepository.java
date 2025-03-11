package ru.autoloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
