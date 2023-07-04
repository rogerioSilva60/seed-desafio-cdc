package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import com.deveficiente.lojadelivro.controller.exception.StandardError.Field;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class MethodArgumentNotValidExceptionHandler {
  private static final String MSG_INVALID_FIELDS = "Um ou mais campos são inválidos, preencha corretamente e tente novamente.";
  private final MessageSource messageSource;

  public MethodArgumentNotValidExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public StandardError createResponse(MethodArgumentNotValidException e, HttpStatus status,
      WebRequest request) {

    String path = ((ServletWebRequest) request).getRequest().getRequestURI();
    StringJoiner stringJoiner = new StringJoiner(", ");
    List<Field> problemFields = prepareProblemFields(e, stringJoiner);

    return StandardError
        .createStandartErrorBuilder(status, stringJoiner.toString(), MSG_INVALID_FIELDS, path)
        .fields(problemFields)
        .build();
  }

  private List<Field> prepareProblemFields(MethodArgumentNotValidException e,
      StringJoiner stringJoiner) {
    return e.getBindingResult().getFieldErrors()
        .stream()
        .map(fieldError -> {
          String messageField = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
          stringJoiner.add(messageField);
          return Field.builder()
              .name(fieldError.getField())
              .userMessage(messageField)
              .build();
        })
        .collect(Collectors.toList());
  }

}
