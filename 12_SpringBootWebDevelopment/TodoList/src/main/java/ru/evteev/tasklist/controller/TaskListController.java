package ru.evteev.tasklist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evteev.tasklist.model.Task;
import ru.evteev.tasklist.model.TaskList;

import java.util.List;

@RestController
public class TaskListController {

    @GetMapping("/tasklist")
    public List<Task> list() {
        return TaskList.getAllTasks();
    }

    // CRUD - Create
    @PostMapping("/tasklist/")
    public int addTask(Task task) {
        int id = TaskList.getSize() + 1;
        task.setId(id);
        TaskList.addTask(task);
        return TaskList.getSize();
    }

    // CRUD - Read
    @GetMapping("/tasklist/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
        Task task = TaskList.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
    }

    // CRUD - Delete
    @PostMapping("/tasklist/{id}")
    public void delTask(@PathVariable int id) {
        TaskList.delTask(id);
    }
}
