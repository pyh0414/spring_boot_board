/*
 * Created by YunGu Kang (yungu@userinsight.co.kr). Copyright (c) ${year} Userinsight Co.
 * <http://www.userinsight.co.kr> All rights reserved.
 */

package kr.co.userinsight.edu.starter.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import kr.co.userinsight.edu.starter.enumerate.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 5, max = 16)
  @Column(unique = true, nullable = false)
  private String username;

  @Size(min = 2, max = 10)
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Transient
  private String passwordConfirm;

  @Column(columnDefinition = "tinyint default b'1'")
  private boolean enabled = true;

  @Column(columnDefinition = "tinyint default b'0'")
  private boolean locked = false;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  private List<Role> roles = new ArrayList<>();

  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name="department_id")
  private Department department;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>(0);
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.name()));
    }
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }


  /**
   * 신규 사용자이거나 비밀번호 란에 비밀번호를 입력한 경우 비밀번호 유효성 검사 필요
   */
  public boolean hasPasswordChanged() {
    return !passwordConfirm.isEmpty() || !password.isEmpty() || id == null;
  }

  public boolean hasValidPassword() {
    return !password.isEmpty() && !passwordConfirm.isEmpty() &&
        this.password.equals(this.passwordConfirm);
  }
}
