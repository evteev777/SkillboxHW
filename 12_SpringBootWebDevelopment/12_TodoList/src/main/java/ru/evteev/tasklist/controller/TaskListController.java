package ru.evteev.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.evteev.tasklist.model.Task;
import ru.evteev.tasklist.model.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskListController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskListController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity<String> createTask(Task task) {
        Task newTask = taskRepository.save(task);
        return new ResponseEntity<>("Task added: ID_" + newTask.getId(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> methodNotAllowed(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(null);
    }

    @GetMapping
    public ResponseEntity<Object> readAllTasks() {

        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> allTasks = (ArrayList<Task>) taskIterable;
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readTask(@PathVariable int id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            return new ResponseEntity<>(optionalTask.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateAllTasks(Task newTask) {
        for (Task oldTask : taskRepository.findAll()) {
            newTask.setId(oldTask.getId());
            taskRepository.save(newTask);
        }
        return new ResponseEntity<>("Tasks updated: " + taskRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, Task newTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            newTask.setId(id);
            taskRepository.save(newTask);
            return new ResponseEntity<>("Task updated: ID_" + id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllTasks() {
        taskRepository.deleteAll();
        return new ResponseEntity<>("Task list cleared", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
            return new ResponseEntity<>("Task deleted: ID_" + id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
