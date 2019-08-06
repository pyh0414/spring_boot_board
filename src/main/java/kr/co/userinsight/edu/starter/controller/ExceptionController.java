package kr.co.userinsight.edu.starter.controller;

import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 시스템에서 발생하는 예외를 Global 하게 처리하기 위해서는 @ControllerAdvice를 사용한다.
 */
@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(value = DataNotFoundException.class)
  public String dataNotFoundException(Exception e) {
    return "error/datanotfound";
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  public String accessDeniedException(Exception e) {
    return "error/403";
  }
}
