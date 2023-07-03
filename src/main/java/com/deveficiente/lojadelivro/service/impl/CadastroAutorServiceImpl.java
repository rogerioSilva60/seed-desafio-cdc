package com.deveficiente.lojadelivro.service.impl;

import com.deveficiente.lojadelivro.entity.Autor;
import com.deveficiente.lojadelivro.repository.AutorRepository;
import com.deveficiente.lojadelivro.service.CadastroAutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CadastroAutorServiceImpl implements CadastroAutorService {

  private final AutorRepository repository;

  @Transactional
  @Override
  public Autor salvar(Autor autor) {
    return repository.save(autor);
  }

}
