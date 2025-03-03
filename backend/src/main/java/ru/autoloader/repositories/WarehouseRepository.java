package ru.autoloader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.autoloader.entities.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
