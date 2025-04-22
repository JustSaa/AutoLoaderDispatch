package ru.autoloader.model.dto;

import ru.autoloader.model.RequestStatus;

// когда заявка создана
public record NewRequestEvent(Long requestId, String warehouseName, RequestStatus status) { }
