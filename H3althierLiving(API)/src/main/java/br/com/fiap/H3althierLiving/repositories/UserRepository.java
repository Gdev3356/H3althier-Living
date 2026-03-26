package br.com.fiap.H3althierLiving.repositories;

import br.com.fiap.H3althierLiving.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

