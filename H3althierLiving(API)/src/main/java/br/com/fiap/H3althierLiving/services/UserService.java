package br.com.fiap.H3althierLiving.services;

import br.com.fiap.H3althierLiving.models.User;
import br.com.fiap.H3althierLiving.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    @Autowired

    private UserRepository repository;

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User addUser(User user){
        return repository.save(user);
    }

    public User getUserById(Long id){
        return findUserById(id);
    }

    public void deleteUser(Long id) {
        findUserById(id);
        repository.deleteById(id);
    }

    public User updateUser(Long id, User newUser) {
        findUserById(id);
        newUser.setId(id);
        return repository.save(newUser);
    }

    private User findUserById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com id " + id + " não encontrado")
        );
    }
}
