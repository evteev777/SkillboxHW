package ru.evteev.tasklist.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskList {

    private static final Map<Integer, Task> TASK_LIST = new HashMap<>();
    private static int id = 0;

    private TaskList() {
    }

    public static void addTask(Task task) {
        id++;
        task.setId(id);
        TASK_LIST.put(id, task);
    }

    public static Task getTask(int id) {
        return TASK_LIST.getOrDefault(id, null);
    }

    public static void delTask(int id) {
        TASK_LIST.remove(id, getTask(id));
    }

    public static List<Task> getAllTasks() {
        return new ArrayList<>(TASK_LIST.values());
    }

    public static int getSize() {
        return TASK_LIST.size();
    }
}
