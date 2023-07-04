package com.deveficiente.lojadelivro.controller.exception;

import com.deveficiente.lojadelivro.controller.exception.handler.DateTimeParseExceptionHandler;
import com.deveficiente.lojadelivro.controller.exception.handler.HttpMessageNotReadableHandler;
import com.deveficiente.lojadelivro.controller.exception.handler.IllegalArgumentExceptionHandler;
import com.deveficiente.lojadelivro.controller.exception.handler.InvalidFormatExceptionHandler;
import com.deveficiente.lojadelivro.controller.exception.handler.JoinPathReference;
import com.deveficiente.lojadelivro.controller.exception.handler.MethodArgumentNotValidExceptionHandler;
import com.deveficiente.lojadelivro.controller.exception.handler.PropertyBindingExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;
  private final JoinPathReference joinPathReference;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    StandardError problem = new MethodArgumentNotValidExceptionHandler(messageSource)
        .createResponse(e, status, request);

    return handleExceptionInternal(e, problem, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    ResponseEntity<Object> response = buildMessageNotReadable(e, headers, status, request);

    return handleExceptionInternal(e, response.getBody(), response.getHeaders(),
        response.getStatusCode(), request);
  }

  private ResponseEntity<Object> buildMessageNotReadable(HttpMessageNotReadableException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    Throwable rootCause = ExceptionUtils.getRootCause(e);
    if(rootCause instanceof InvalidFormatException) {
      return createMessageNotReadable(new InvalidFormatExceptionHandler(joinPathReference),
          e, request, status, headers);
    } else if (rootCause instanceof PropertyBindingException) {
      return createMessageNotReadable(new PropertyBindingExceptionHandler(joinPathReference),
          e, request, status, headers);
    } else if (rootCause instanceof IllegalArgumentException) {
      return createMessageNotReadable(new IllegalArgumentExceptionHandler(), e, request, status,
          headers);
    } else if (rootCause instanceof DateTimeParseException) {
      return createMessageNotReadable(new DateTimeParseExceptionHandler(), e, request, status,
          headers);
    } else {
      return createMessageNotReadable(new HttpMessageNotReadableHandler(), e, request, status,
          headers);
    }
  }

  private ResponseEntity<Object> createMessageNotReadable(
      HttpMessageNotReadableHandler httpMessageNotReadableHandler, HttpMessageNotReadableException e,
      WebRequest request, HttpStatus status, HttpHeaders headers) {
    return httpMessageNotReadableHandler.createResponse(e, request, status, headers);
  }

}
