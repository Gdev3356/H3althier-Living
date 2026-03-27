package br.com.fiap.H3althierLiving.exceptions;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Já existe um usuário cadastrado com o e-mail: " + email);
    }
}