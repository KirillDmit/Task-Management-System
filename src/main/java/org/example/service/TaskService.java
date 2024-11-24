package org.example.service;

import org.example.entity.Priority;
import org.example.entity.Task;
import org.example.entity.User;
import org.example.exception.TaskNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task, Long userId, Long assigneeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new UserNotFoundException("Assignee not found with id " + assigneeId));

        task.setUser(user);
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public Task addComment(Long taskId, String comment) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskId));
        task.getComments().add(comment);
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskId));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setAssignee(updatedTask.getAssignee());

        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskId));
    }

    public List<Task> getTasksByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Page<Task> getTasks(String authorEmail, String assigneeEmail, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (authorEmail != null && assigneeEmail != null) {
            return taskRepository.findByAuthorEmailAndAssigneeEmail(authorEmail, assigneeEmail, pageRequest);
        } else if (authorEmail != null) {
            return taskRepository.findByAuthorEmail(authorEmail, pageRequest);
        } else if (assigneeEmail != null) {
            return taskRepository.findByAssigneeEmail(assigneeEmail, pageRequest);
        } else {
            return taskRepository.findAll(pageRequest);
        }
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + taskId));
        taskRepository.delete(task);
    }

    public boolean isUserAssignedToTask(Long taskId, String username) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return task.getAssignee().getUsername().equals(username);
    }
}

