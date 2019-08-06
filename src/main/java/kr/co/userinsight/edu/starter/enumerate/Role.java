package kr.co.userinsight.edu.starter.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
  ROLE_MANAGE_USER("사용자 관리"),
  ROLE_MANAGE_NOTICE("공지사항 관리"),
  ROLE_MANAGE_FREEBOARD("자유게시판 관리"),
  ROLE_MANAGE_DEPARTMENT("부서 관리");

  private String desc;
}
