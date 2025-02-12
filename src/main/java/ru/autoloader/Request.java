package ru.autoloader;

import java.util.Objects;

public class Request {
    private final int id;
    private final String type;


    public Request(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        return id == request.id && Objects.equals(type, request.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
