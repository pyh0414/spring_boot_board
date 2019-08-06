/*
 * Created by Kyungrim Choi (rimi@userinsight.co.kr).
 * Copyright (c) 2019 Userinsight Co. <http://www.userinsight.co.kr>
 * All rights reserved.
 */

package kr.co.userinsight.edu.starter.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class CustomAuthenticationHandler
    implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

  private static final String SUCCESS_URL = "/";
  private static final String FAILURE_URL = "/login";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication auth) throws IOException {

    response.sendRedirect(request.getContextPath() + SUCCESS_URL);
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    String username = request.getParameter("username");
//
//    request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
//    request.setAttribute("SPRING_SECURITY_LAST_USERNAME", username);
    request.setAttribute("username", username);
    request.setAttribute("error", getErrorMessage(exception));
    request.getRequestDispatcher(FAILURE_URL).forward(request, response);
  }

  /**
   * Spring Security 가 반환하는 에러 메시지 얻어옴
   *
   * @return 에러메시지
   */
  private String getErrorMessage(AuthenticationException exception) {

    String error;
    if (exception instanceof BadCredentialsException) {
      error = "아이디 또는 비밀번호를 확인해주시기 바랍니다.";
    } else if (exception instanceof LockedException) {
      error = "해당 계정은 관리자에 의해 잠겨있는 계정입니다.";
    } else if (exception instanceof InternalAuthenticationServiceException) {
      error = "존재하지 않는 계정입니다.";
    } else if (exception instanceof DisabledException) {
      error = "로그인 반복 실패 또는 관리자에 의해 잠겨있는 계정입니다.\n관리자에게 문의하세요.";
    } else {
      error = null;
    }

    return error;
  }
}
