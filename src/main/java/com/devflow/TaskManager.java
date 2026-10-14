package com.devflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();

    public Task add(String title) {
        Task task = new Task(tasks.size() + 1, title, false);
        tasks.add(task);
        return task;
    }

    public List<Task> list() {
        return List.copyOf(tasks);
    }

    public Optional<Task> complete(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id() == id) {
                Task done = tasks.get(i).markDone();
                tasks.set(i, done);
                return Optional.of(done);
            }
        }
        return Optional.empty();
    }

    public boolean delete(int id) {
        return tasks.removeIf(task -> task.id() == id);
    }

    public int clear() {
        int removed = tasks.size();
        tasks.clear();
        return removed;
    }

    public long pendingCount() {
        return tasks.stream().filter(task -> !task.done()).count();
    }
}
