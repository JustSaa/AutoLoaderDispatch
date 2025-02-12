package ru.autoloader;

import java.util.LinkedList;
import java.util.Queue;

public class RequestQueue {
    private final Queue<Request> requestQueue = new LinkedList<>();

    public void addRequest(Request request) {
        requestQueue.offer(request);
    }

    public Request getNextRequest() {
        return requestQueue.poll();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public int size() {
        return requestQueue.size();
    }
}
