package com.todoApp.todo_withdb.tasks;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        var taskEntity = modelMapper.map(task, TaskEntity.class);
        var savedTask = tasksRepository.save(taskEntity);
        return modelMapper.map(savedTask, TaskDto.class);
    }
}
