package com.oocl.ita.web.core.exception.response;


public enum CoreErrorResponse implements ErrorResponse {
  EMAIL_EXISTS_ERROR(200001, "Email already exists")
  ;
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
