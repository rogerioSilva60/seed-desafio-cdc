package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
public class InvalidFormatExceptionHandler extends HttpMessageNotReadableHandler {

  private static final String MSG_PROPERTY_INVALID = "A propriedade '%s' recebeu o valor '%s', é um tipo inválido. "
      + "Corriga e insira um valor compatível com o tipo '%s'. ";

  private final JoinPathReference joinPathReference;

  @Override
  public ResponseEntity<Object> createResponse(HttpMessageNotReadableException e, WebRequest request,
      HttpStatus status, HttpHeaders headers) {

    if(ExceptionUtils.getRootCause(e) instanceof InvalidFormatException invalidFormatException) {
      String pathProperty = joinPathReference.joinPath(invalidFormatException.getPath());
      String path = ((ServletWebRequest)request).getRequest().getRequestURI();
      String message = String.format(MSG_PROPERTY_INVALID, pathProperty, invalidFormatException.getValue(),
          invalidFormatException.getTargetType().getSimpleName());
      var response = StandardError.createStandartErrorBuilder(status, MSG_ERROR_BODY_INVALID,
              message, path)
          .build();
      return new ResponseEntity<>(response, headers, status);
    }

    return super.createResponse(e, request, status, headers);
  }
}
