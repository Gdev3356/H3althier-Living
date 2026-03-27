package br.com.fiap.H3althierLiving.controllers;

import br.com.fiap.H3althierLiving.models.Task;
import br.com.fiap.H3althierLiving.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<List<Task>> listAll() {
        return ResponseEntity.ok(service.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addTask(task));
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        log.info("Obtendo dados da tarefa {}", id);
        return ResponseEntity.ok(service.getTaskById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        log.info("Deletando tarefa com id {}", id );
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
        log.info("Atualizando tarefa com id {} com os dados {}", id, task);
        return ResponseEntity.ok( service.updateTask(id, task) );
    }
}
