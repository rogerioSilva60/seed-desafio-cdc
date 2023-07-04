package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
public class PropertyBindingExceptionHandler extends  HttpMessageNotReadableHandler {

  private static final String MSG_PROPERTY_NOT_EXIST = "A propriedade '%s' não existe. "
      + "Corrija ou remova esta propriedade e tente novamente.";

  private final JoinPathReference joinPathReference;

  @Override
  public ResponseEntity<Object> createResponse(HttpMessageNotReadableException e, WebRequest request,
      HttpStatus status, HttpHeaders headers) {

    if(ExceptionUtils.getRootCause(e) instanceof PropertyBindingException propertyBindingException) {
      String pathProperty = joinPathReference.joinPath(propertyBindingException.getPath());
      String path = ((ServletWebRequest)request).getRequest().getRequestURI();
      String message = String.format(MSG_PROPERTY_NOT_EXIST, pathProperty);
      var response = StandardError
          .createStandartErrorBuilder(status, MSG_ERROR_BODY_INVALID, message, path)
          .build();
      return new ResponseEntity<>(response, headers, status);
    }

    return super.createResponse(e, request, status, headers);
  }
}
