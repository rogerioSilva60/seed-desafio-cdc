package com.deveficiente.lojadelivro.controller.exception.handler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class JoinPathReference {

  public String joinPath(List<Reference> references) {
    return references.stream()
        .map(Reference::getFieldName)
        .collect(Collectors.joining("."));
  }

}
