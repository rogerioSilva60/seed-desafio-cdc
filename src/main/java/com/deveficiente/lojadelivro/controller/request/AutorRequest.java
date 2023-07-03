package com.deveficiente.lojadelivro.controller.request;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class AutorRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank
  private String nome;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 1, max = 400)
  private String descricao;

}
