package ru.evteev.tasklist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.evteev.tasklist.model.Task;
import ru.evteev.tasklist.model.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    private final TaskRepository taskRepository;

    @Autowired
    public DefaultController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> taskList = new ArrayList<>();
        for (Task task : taskIterable) {
            taskList.add(task);
        }
        model.addAttribute("tasks", taskList);
        model.addAttribute("tasksCount", taskList.size());
        return "index";
    }
}
