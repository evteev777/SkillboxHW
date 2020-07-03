package ru.evteev.todolist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DefaultController {

    @GetMapping(value = "/")
    public String index() {
        return LocalDate.now().toString();
    }
}
