package com.todoApp.todo_withdb.tasks;

import com.todoApp.todo_withdb.common.ErrorResponseaDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TasksService {
    private  TasksRepository tasksRepository;
    private ModelMapper modelMapper;

    public TasksService(TasksRepository tasksRepository, ModelMapper modelMapper) {
         this.tasksRepository = tasksRepository;
         this.modelMapper = modelMapper; // Spring does not know how to create a model mapper so go to main calss and add beans
    }

    public List<TaskDto> getAllTasks() {
        return tasksRepository.findAll()
                .stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long id) {
        var task = tasksRepository.findById(id).orElse(null);
        // TODO: handle task not found
        return modelMapper.map(task, TaskDto.class);
    }

    public TaskDto createNewTask(TaskDto task) {
        if(task.getDueDate() != null && task.getDueDate().before(new Date())) {
           throw new TaskInvalidException("Due date must be in the future");
        }

        var taskEntity = modelMapper.map(task, TaskEntity.class);
        var savedTask = tasksRepository.save(taskEntity);

        //TODO: TaskAlreadyExistsException
        return modelMapper.map(savedTask, TaskDto.class);
    }

    static class TaskNotFoundException extends IllegalArgumentException {
        public TaskNotFoundException(Long id) {
            super("Task with id " + id + " not found");
        }
    }

    static class TaskAlreadyExistsException extends IllegalArgumentException {
        public TaskAlreadyExistsException(Long id) {
            super("Task with id " + " already exists") ;
        }
    }

    static class TaskInvalidException extends IllegalArgumentException {
        public TaskInvalidException(String message) {
            super(message);
        }
    }

    @ExceptionHandler({
            TasksService.TaskNotFoundException.class,
            TaskAlreadyExistsException.class,
            TaskInvalidException.class
    })
    ResponseEntity<ErrorResponseaDto> handleError(Exception e) {
        if(e instanceof TasksService.TaskNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseaDto(e.getMessage()));
        }
        if(e instanceof TasksService.TaskAlreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseaDto(e.getMessage()));
        }
        if(e instanceof TasksService.TaskInvalidException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseaDto(e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseaDto(e.getMessage()));

    }
}
