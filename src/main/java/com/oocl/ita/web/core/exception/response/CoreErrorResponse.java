package com.oocl.ita.web.core.exception.response;


public enum CoreErrorResponse implements ErrorResponse {
  EMAIL_EXISTS_ERROR(200001, "Email already exists"),
  ENTITY_NOT_EXIST_ERROR(200002, "%s does not exist"),
  CONCERT_IN_PROGRESS_ERROR(200003, "Concert is in progress"),
  TICKET_LIMIT_EXCEEDED_ERROR(200004, "You have purchased more than 3 tickets"),
  NOT_ENOUGH_TICKETS_ERROR(200005, "Not enough tickets available"),
  TICKET_SALE_NOT_STARTED(200006, "Ticket sale not started"),
  UNKNOWN_ERROR(200007, "error");
  private final int code;

  private final String message;

  private CoreErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String toString() {
    return toJsonString();
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
