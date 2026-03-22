package br.com.fiap.H3althierLiving.services;

import br.com.fiap.H3althierLiving.models.Task;
import br.com.fiap.H3althierLiving.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    @Autowired

    private TaskRepository repository;

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Task addTask(Task task){
        return repository.save(task);
    }

    public Task getTaskById(Long id){
        return findTaskById(id);
    }

    public void deleteTask(Long id) {
        findTaskById(id);
        repository.deleteById(id);
    }

    public Task updateTask(Long id, Task newTask) {
        findTaskById(id);
        newTask.setId(id);
        return repository.save(newTask);
    }

    private Task findTaskById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa com id " + id + " não encontrado")
        );
    }
}
