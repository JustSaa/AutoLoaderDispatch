package ru.autoloader.model.dto;

// когда погрузчик назначен
public record LoaderAssignedEvent(Long requestId, Long loaderId, String loaderName) { }
