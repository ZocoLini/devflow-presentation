package com.devflow;

public record Task(int id, String title, boolean done) {

    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        title = title.strip();
    }

    public Task markDone() {
        return new Task(id, title, true);
    }

    @Override
    public String toString() {
        return "%d. [%s] %s".formatted(id, done ? "x" : " ", title);
    }
}
