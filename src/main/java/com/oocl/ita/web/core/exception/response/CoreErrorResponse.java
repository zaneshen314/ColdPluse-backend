package com.oocl.ita.web.core.exception.response;


public enum CoreErrorResponse implements ErrorResponse {
  EMAIL_EXISTS_ERROR(200001, "Email already exists"),
  ENTITY_NOT_EXIST_ERROR(200002, "%d does not exist"),
  CONCERT_IN_PROGRESS_ERROR(200003, "Concert is in progress"),;
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
