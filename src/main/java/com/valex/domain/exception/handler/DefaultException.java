package com.valex.domain.exception.handler;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultException {
  private Date date;
  private String message;
  public DefaultException (
      Date date,
      String message
  ) {
    super();

    this.date = date;
    this.message = message;
  }
}
