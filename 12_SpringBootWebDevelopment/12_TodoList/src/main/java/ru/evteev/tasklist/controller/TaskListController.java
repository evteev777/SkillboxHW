package ru.evteev.tasklist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evteev.tasklist.model.Task;
import ru.evteev.tasklist.model.TaskList;

@RestController
public class TaskListController {
    
    private static final String PATH = "/tasklist";
    private static final String PATH_ID = PATH + "/{id}";

    @PostMapping(PATH)
    public ResponseEntity<String> createTask(Task task) {
        TaskList.addTask(task);
        return new ResponseEntity<>("Task added: ID_" + task.getId(), HttpStatus.OK);
    }

    @PostMapping(PATH_ID)
    public ResponseEntity<Object> methodNotAllowed() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @GetMapping(PATH)
    public ResponseEntity<Object> readAllTasks() {
        return new ResponseEntity<>(TaskList.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping(PATH_ID)
    public ResponseEntity<Object> readTask(@PathVariable int id) {

        Task task = TaskList.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
    }

    @PutMapping(PATH)
    public ResponseEntity<String> updateAllTasks(Task newTask) {
        TaskList.updateAllTasks(newTask);
        return new ResponseEntity<>("Tasks updated: " + TaskList.getSize(), HttpStatus.OK);
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<String> updateTask(@PathVariable int id, Task newTask) {
        Task oldTask = TaskList.getTask(id);
        if (oldTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            TaskList.updateTask(oldTask, newTask);
            return new ResponseEntity<>("Task updated: ID_" + id, HttpStatus.OK);
        }
    }

    @DeleteMapping(PATH)
    public ResponseEntity<String> deleteAllTasks() {
        TaskList.deleteAllTasks();
        return new ResponseEntity<>("Task list cleared", HttpStatus.OK);
    }

    @DeleteMapping(PATH_ID)
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        Task task = TaskList.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            TaskList.deleteTask(id);
            return new ResponseEntity<>("Task deleted: ID_" + id, HttpStatus.OK);
        }
    }
}
