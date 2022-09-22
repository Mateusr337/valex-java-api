package com.valex.domain.exception.handler;

import com.valex.domain.exception.BadRequestException;
import com.valex.domain.exception.ConflictException;
import com.valex.domain.exception.NotFoundException;
import com.valex.domain.exception.UnauthorizedException;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

  @ExceptionHandler (NotFoundException.class)
  @ResponseStatus (HttpStatus.NOT_FOUND)
  public DefaultException notFound (NotFoundException e) {
    return new DefaultException(
        new Date(),
        e.getMessage()
    );
  }

  @ExceptionHandler (ConflictException.class)
  @ResponseStatus (HttpStatus.CONFLICT)
  public DefaultException conflict (ConflictException e) {
    return new DefaultException(
        new Date(),
        e.getMessage()
    );

  }

  @ExceptionHandler (UnauthorizedException.class)
  @ResponseStatus (HttpStatus.UNAUTHORIZED)
  public DefaultException unauthorized (UnauthorizedException e) {
    return new DefaultException(
        new Date(),
        e.getMessage()
    );

  }

  @ExceptionHandler (BadRequestException.class)
  @ResponseStatus (HttpStatus.BAD_REQUEST)
  public DefaultException unauthorized (BadRequestException e) {
    return new DefaultException(
        new Date(),
        e.getMessage()
    );
  }

  @ExceptionHandler (MethodArgumentNotValidException.class)
  @ResponseStatus (HttpStatus.BAD_REQUEST)
  public DefaultException notValidRequest (MethodArgumentNotValidException e) {
    return new DefaultException(
        new Date(),
        e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .toList()
            .toString()
    );
  }

}
