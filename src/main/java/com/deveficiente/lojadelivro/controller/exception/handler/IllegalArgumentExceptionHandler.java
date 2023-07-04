package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import com.deveficiente.lojadelivro.controller.exception.StandardError.StandardErrorBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class IllegalArgumentExceptionHandler extends HttpMessageNotReadableHandler {

  @Override
  public ResponseEntity<Object> createResponse(HttpMessageNotReadableException e, WebRequest request,
      HttpStatus status, HttpHeaders headers) {

    if(ExceptionUtils.getRootCause(e) instanceof IllegalArgumentException illegalArgumentException) {
      String path = ((ServletWebRequest)request).getRequest().getRequestURI();
      StandardErrorBuilder response = StandardError.createStandartErrorBuilder(status,
          MSG_ERROR_BODY_INVALID, illegalArgumentException.getMessage(), path);
      return new ResponseEntity<>(response, headers, status);
    }

    return super.createResponse(e, request, status, headers);
  }
}
