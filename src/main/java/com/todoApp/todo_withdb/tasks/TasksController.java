package com.todoApp.todo_withdb.tasks;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("")
    ResponseEntity<List<TaskDto>> getAllTasks() {
        var tasks = tasksService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("")
    ResponseEntity<TaskDto> createNewTask(@RequestBody TaskDto taskDto) {
        var savedTask = tasksService.createNewTask(taskDto);
        return ResponseEntity.created(URI.create("/tasks" + savedTask.getId())).body(savedTask);
    }


    @GetMapping("/{id}")
    ResponseEntity<TaskDto> getTaskById(@PathVariable("id") Long id) {
        var task = tasksService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //@PostMapping("/{id}")
}
