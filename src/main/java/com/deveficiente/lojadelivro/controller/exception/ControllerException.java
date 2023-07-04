package com.deveficiente.lojadelivro.controller.exception;

import com.deveficiente.lojadelivro.controller.exception.handler.MethodArgumentNotValidExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    StandardError problem = new MethodArgumentNotValidExceptionHandler(messageSource)
        .createResponse(e, status, request);

    return handleExceptionInternal(e, problem, headers, status, request);
  }

}
