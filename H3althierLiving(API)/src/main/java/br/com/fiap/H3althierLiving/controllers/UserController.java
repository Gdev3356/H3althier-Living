package br.com.fiap.H3althierLiving.controllers;

import br.com.fiap.H3althierLiving.models.User;
import br.com.fiap.H3althierLiving.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addUser(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        log.info("Obtendo dados do usuário {}", id);
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        log.info("Deletando usuário com id {}", id );
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        log.info("Atualizando usuário com id {} com os dados {}", id, user);
        return ResponseEntity.ok( service.updateUser(id, user) );
    }
}
