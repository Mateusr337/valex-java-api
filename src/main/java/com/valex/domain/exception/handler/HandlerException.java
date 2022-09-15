package com.valex.domain.exception.handler;

import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.DefaultException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.exception.UnauthorizedException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

  @ExceptionHandler ( NotFoundException.class )
  public ResponseEntity<DefaultException> notFound (
      NotFoundException e,
      HttpServletRequest request
      ) {
    DefaultException exception = new DefaultException(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        e.getMessage()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
  }

  @ExceptionHandler ( ConflictException.class )
  public ResponseEntity<DefaultException> conflict (
      ConflictException e,
      HttpServletRequest request
  ) {
    DefaultException exception = new DefaultException(
        HttpStatus.CONFLICT.value(),
        new Date(),
        e.getMessage()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
  }

  @ExceptionHandler ( UnauthorizedException.class )
  public ResponseEntity<DefaultException> unauthorized (
      UnauthorizedException e,
      HttpServletRequest request
  ) {
    DefaultException exception = new DefaultException(
        HttpStatus.UNAUTHORIZED.value(),
        new Date(),
        e.getMessage()
    );

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
  }

}
