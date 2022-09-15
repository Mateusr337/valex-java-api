package com.valex.domain.exception;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultException {

  private Integer status;
  private Date date;
  private String message;
  public DefaultException (
      Integer status,
      Date date,
      String message
  ) {
    super();

    this.status = status;
    this.date = date;
    this.message = message;
  }
}
