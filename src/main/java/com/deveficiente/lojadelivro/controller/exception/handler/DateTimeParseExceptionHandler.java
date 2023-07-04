package com.deveficiente.lojadelivro.controller.exception.handler;

import com.deveficiente.lojadelivro.controller.exception.StandardError;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class DateTimeParseExceptionHandler extends HttpMessageNotReadableHandler {

  private static final String MSG_DATA_PARSE_INVALID = "Forneça uma data válida";
  private static final String MSG_PROPERTY_DATE_TIME_PARSE_INVALID = "A propriedade recebeu o valor "
      + "'%s'é um tipo inválido, Corrija e insira um valor compatível com o tipo '%s'";

  @Override
  public ResponseEntity<Object> createResponse(HttpMessageNotReadableException e, WebRequest request,
      HttpStatus status, HttpHeaders headers) {

    if(ExceptionUtils.getRootCause(e) instanceof DateTimeParseException dateTimeParseException) {
      String path = ((ServletWebRequest)request).getRequest().getRequestURI();
      String message = String.format(MSG_PROPERTY_DATE_TIME_PARSE_INVALID,
          dateTimeParseException.getParsedString(), "yyyy-MM-dd");
      StandardError response = StandardError.createStandartErrorBuilder(status,
          MSG_DATA_PARSE_INVALID, message, path).build();
      return new ResponseEntity<>(response, headers, status);
    }

    return super.createResponse(e, request, status, headers);
  }
}
