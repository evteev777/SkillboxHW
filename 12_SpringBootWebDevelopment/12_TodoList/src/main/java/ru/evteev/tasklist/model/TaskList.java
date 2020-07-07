package ru.evteev.tasklist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskList {

    private static final Map<Integer, Task> TASK_LIST = new ConcurrentHashMap<>();
    private static final AtomicInteger id = new AtomicInteger();

    private TaskList() {
    }

    public static void addTask(Task task) {
        id.incrementAndGet();
        task.setId(id.intValue());
        TASK_LIST.put(id.intValue(), task);
    }

    public static Task getTask(int id) {
        return TASK_LIST.getOrDefault(id, null);
    }

    public static List<Task> getAllTasks() {
        return new ArrayList<>(TASK_LIST.values());
    }

    public static void updateTask(Task oldTask, Task newTask) {
        oldTask.setName(newTask.getName());
        oldTask.setCompleted(newTask.isCompleted());
    }

    public static void updateAllTasks(Task newTask) {
        TASK_LIST.values().forEach(t -> {
            t.setName(newTask.getName());
            t.setCompleted(newTask.isCompleted());
        });
    }

    public static void deleteTask(int id) {
        TASK_LIST.remove(id);
    }

    public static void deleteAllTasks() {
        TASK_LIST.clear();
    }

    public static int getSize() {
        return TASK_LIST.size();
    }
}
