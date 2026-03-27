package br.com.fiap.H3althierLiving.services;

import br.com.fiap.H3althierLiving.exceptions.BusinessException;
import br.com.fiap.H3althierLiving.exceptions.DuplicateEmailException;
import br.com.fiap.H3althierLiving.exceptions.ResourceNotFoundException;
import br.com.fiap.H3althierLiving.models.User;
import br.com.fiap.H3althierLiving.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        // Regra 1: Nome obrigatório
        if (user.getName() == null || user.getName().isBlank()) {
            throw new BusinessException("O nome do usuário não pode estar vazio.");
        }

        // Regra 2: E-mail duplicado
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(user.getEmail());
        }

        // Regra 3: Senha mínima de 6 caracteres
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new BusinessException("A senha deve ter no mínimo 6 caracteres.");
        }

        // Regra 4: Idade mínima de 13 anos para usar o app
        if (user.getAge() < 13) {
            throw new BusinessException("O usuário deve ter pelo menos 13 anos para se cadastrar.");
        }

        // Regra 5: Peso e altura devem ser positivos se informados
        if (user.getWeight() != null && user.getWeight() <= 0) {
            throw new BusinessException("O peso deve ser um valor positivo.");
        }
        if (user.getHeight() != null && user.getHeight() <= 0) {
            throw new BusinessException("A altura deve ser um valor positivo.");
        }

        // Regra 6: Meta de passos deve ser positiva se informada
        if (user.getDailyStepGoal() != null && user.getDailyStepGoal() <= 0) {
            throw new BusinessException("A meta diária de passos deve ser maior que zero.");
        }

        return repository.save(user);
    }

    public User getUserById(Long id) {
        return findUserById(id);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        repository.deleteById(id);
    }

    public User updateUser(Long id, User newUser) {
        findUserById(id);

        // Regra: Não permite trocar para e-mail já usado por outro usuário
        if (repository.existsByEmailAndIdNot(newUser.getEmail(), id)) {
            throw new DuplicateEmailException(newUser.getEmail());
        }

        // Reutiliza as mesmas validações de campos
        if (newUser.getWeight() != null && newUser.getWeight() <= 0) {
            throw new BusinessException("O peso deve ser um valor positivo.");
        }
        if (newUser.getHeight() != null && newUser.getHeight() <= 0) {
            throw new BusinessException("A altura deve ser um valor positivo.");
        }
        if (newUser.getDailyStepGoal() != null && newUser.getDailyStepGoal() <= 0) {
            throw new BusinessException("A meta diária de passos deve ser maior que zero.");
        }

        newUser.setId(id);
        return repository.save(newUser);
    }

    private User findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }
}
