package org.example.repository;

import org.example.entity.Priority;
import org.example.entity.Task;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatusAndPriority(String status, Priority priority);
    List<Task> findByUserId(Long userId);
    Page<Task> findByAuthorEmail(String authorEmail, Pageable pageable);
    Page<Task> findByAssigneeEmail(String assigneeEmail, Pageable pageable);
    Page<Task> findByAuthorEmailAndAssigneeEmail(String authorEmail, String assigneeEmail, Pageable pageable);
}
