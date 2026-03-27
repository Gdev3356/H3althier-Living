package br.com.fiap.H3althierLiving.services;

import br.com.fiap.H3althierLiving.exceptions.BusinessException;
import br.com.fiap.H3althierLiving.exceptions.ResourceNotFoundException;
import br.com.fiap.H3althierLiving.models.Task;
import br.com.fiap.H3althierLiving.models.User;
import br.com.fiap.H3althierLiving.repositories.TaskRepository;
import br.com.fiap.H3althierLiving.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository userRepository;

    private static final int LIMITE_TAREFAS_POR_USUARIO = 20;

    // Limite razoável de impacto calórico por tarefa (ex: uma refeição absurda ou treino extremo)
    private static final int CALORIE_IMPACT_MIN = -3000;
    private static final int CALORIE_IMPACT_MAX = 5000;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task addTask(Task task) {
        // Regra 1: Descrição obrigatória
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new BusinessException("A descrição da tarefa não pode estar vazia.");
        }

        // Regra 2: Usuário associado é obrigatório e deve existir
        if (task.getUser() == null || task.getUser().getId() == null) {
            throw new BusinessException("A tarefa deve estar associada a um usuário.");
        }

        User user = userRepository.findById(task.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", task.getUser().getId()));

        // Regra 3: Limite de tarefas por usuário
        long totalTarefas = repository.countByUser(user);
        if (totalTarefas >= LIMITE_TAREFAS_POR_USUARIO) {
            throw new BusinessException(
                    "Limite de " + LIMITE_TAREFAS_POR_USUARIO + " tarefas por usuário atingido.");
        }

        // Regra 4: calorieImpact dentro de limites razoáveis
        if (task.getCalorieImpact() < CALORIE_IMPACT_MIN || task.getCalorieImpact() > CALORIE_IMPACT_MAX) {
            throw new BusinessException(
                    "O impacto calórico deve estar entre " + CALORIE_IMPACT_MIN + " e " + CALORIE_IMPACT_MAX + " kcal.");
        }

        return repository.save(task);
    }

    public Task getTaskById(Long id) {
        return findTaskById(id);
    }

    public void deleteTask(Long id) {
        findTaskById(id);
        repository.deleteById(id);
    }

    public Task updateTask(Long id, Task newTask) {
        findTaskById(id);

        // Regra: Descrição obrigatória também no update
        if (newTask.getDescription() == null || newTask.getDescription().isBlank()) {
            throw new BusinessException("A descrição da tarefa não pode estar vazia.");
        }

        // Regra: calorieImpact no update também
        if (newTask.getCalorieImpact() < CALORIE_IMPACT_MIN || newTask.getCalorieImpact() > CALORIE_IMPACT_MAX) {
            throw new BusinessException(
                    "O impacto calórico deve estar entre " + CALORIE_IMPACT_MIN + " e " + CALORIE_IMPACT_MAX + " kcal.");
        }

        newTask.setId(id);
        return repository.save(newTask);
    }

    private Task findTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));
    }
}
