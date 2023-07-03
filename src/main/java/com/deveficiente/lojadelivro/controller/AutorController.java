package com.deveficiente.lojadelivro.controller;

import com.deveficiente.lojadelivro.controller.mapper.AutorMapper;
import com.deveficiente.lojadelivro.controller.request.AutorRequest;
import com.deveficiente.lojadelivro.controller.response.AutorResponse;
import com.deveficiente.lojadelivro.entity.Autor;
import com.deveficiente.lojadelivro.service.CadastroAutorService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/autores", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutorController {

  public static final String ID = "/{id}";

  private final CadastroAutorService cadastroAutorService;
  private final AutorMapper autorMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<AutorResponse> salvar(@RequestBody @Valid AutorRequest autorRequest) {
    Autor autorSalvo = cadastroAutorService.salvar(autorMapper.toAutorRequest(autorRequest));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path(ID)
        .buildAndExpand(autorSalvo.getId()).toUri();
    return ResponseEntity.created(uri).body(autorMapper.toAutorResponse(autorSalvo));
  }

}
