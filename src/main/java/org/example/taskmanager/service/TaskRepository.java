package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}
