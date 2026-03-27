package br.com.fiap.H3althierLiving.repositories;

import br.com.fiap.H3althierLiving.models.Task;
import br.com.fiap.H3althierLiving.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByUser(User user);
}

