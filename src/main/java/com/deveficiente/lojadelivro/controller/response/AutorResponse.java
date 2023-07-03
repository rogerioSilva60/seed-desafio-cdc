package com.deveficiente.lojadelivro.controller.response;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
public class AutorResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String nome;
  private String email;
  private String descricao;

}
