package br.com.fiap.H3althierLiving.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resource, Long id) {
    super(resource + " com id " + id + " não encontrado(a)");
  }
}