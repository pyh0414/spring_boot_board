/*
 * Created by YunGu Kang (yungu@userinsight.co.kr). Copyright (c) 2017 Userinsight Co.
 * <http://www.userinsight.co.kr> All rights reserved.
 */

package kr.co.userinsight.edu.starter.config;

import kr.co.userinsight.edu.starter.handler.CustomAccessDeniedHandler;
import kr.co.userinsight.edu.starter.handler.CustomAuthenticationHandler;
import kr.co.userinsight.edu.starter.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String LOGIN_URL = "/login";
  private static final String LOGIN_PROCESS = "/login/process";
  private static final String LOGOUT_URL = "/logout";
  private static final String PARAM_USERNAME = "username";
  private static final String PARAM_PASSWORD = "password";
  private static final String PARAM_REMEMBER = "remember-me";
  private static final int TOKEN_VALIDITY_TIME = 604800;
  private static final String REMEMBER_KEY = "rememberkey123456";

  @NonNull
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  @NonNull
  private final UserService userService;

  @Bean
  public AuthenticationFailureHandler customAuthenticationFailureHandler() {
    return new CustomAuthenticationHandler();
  }

  @Bean
  public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    return new CustomAuthenticationHandler();
  }

  @Bean
  public AccessDeniedHandler customAccessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  /**
   * 유저 DB의 DataSource와 Query 및 Password Encoder 설정.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }

  /**
   * Spring Security에서 인증받지 않아도 되는 리소스 URL 패턴을 지정해 줍니다.
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/static/**");
  }

  /**
   * Spring Security에 의해 인증받아야 할 URL 또는 패턴을 지정해 줍니다.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.headers().frameOptions().sameOrigin()
        .and().authorizeRequests().antMatchers(LOGIN_URL).permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable().formLogin().loginPage(LOGIN_URL).
        loginProcessingUrl(LOGIN_PROCESS)
        .successHandler(customAuthenticationSuccessHandler())
        .failureHandler(customAuthenticationFailureHandler())
        .usernameParameter(PARAM_USERNAME).passwordParameter(PARAM_PASSWORD)
        .and().logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL)).logoutSuccessUrl(LOGIN_URL)
        .and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
        .and().rememberMe().key(REMEMBER_KEY).rememberMeParameter(PARAM_REMEMBER).tokenValiditySeconds(TOKEN_VALIDITY_TIME);
  }
}
