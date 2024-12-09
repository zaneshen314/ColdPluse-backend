package com.oocl.ita.web.core.exception.response;

public interface ErrorResponse {
  int getCode();

  String getMessage();

  default String toJsonString() {
    return String.format("{\"code\":\"%s\",\"message\":\"%s\"}", getCode(), getMessage());
  }

  default String toJsonString(String message) {
    return String.format("{\"code\":\"%s\",\"message\":\"%s\"}", getCode(), message);
  }
}