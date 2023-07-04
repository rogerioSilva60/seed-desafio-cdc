package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class HttpMessageNotReadableHandler {

  protected static final String MSG_ERROR_BODY_INVALID = "O corpo da solicitacão é inválido. "
      + "Verifique o erro de sintaxe.";

  public ResponseEntity<Object> createResponse(HttpMessageNotReadableException e, WebRequest request,
      HttpStatus status, HttpHeaders headers) {
    String path = ((ServletWebRequest) request).getRequest().getRequestURI();
    StandardError response = StandardError
        .createStandartErrorBuilder(status, MSG_ERROR_BODY_INVALID, e.getMessage(), path)
        .build();
    return new ResponseEntity<>(response, new HttpHeaders(), status);
  }

}
