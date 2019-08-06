package kr.co.userinsight.edu.starter.controller;


import java.util.List;
import javax.validation.Valid;

import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.domain.User;
import kr.co.userinsight.edu.starter.enumerate.Role;
import kr.co.userinsight.edu.starter.service.DepartmentService;
import kr.co.userinsight.edu.starter.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  @NonNull
  private final UserService userService;
  @NonNull
  private final DepartmentService departmentService;
  @NonNull
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Secured(value = "ROLE_MANAGE_USER")
  @GetMapping("/list")
  public String userList(Model model,
      @SortDefault(value = "id", direction = Direction.ASC) Pageable pageable) {
    Page<User> users = userService.findAll(pageable);
    model.addAttribute("users", users);
    return "user/list";
  }

  @Secured(value = "ROLE_MANAGE_USER")
  @GetMapping(value = {"/edit", "/edit/{id}"})
  public String userEdit(Model model, @PathVariable(name = "id", required = false) Long id)
      throws DataNotFoundException {
    User user = new User();
    if (id != null) {
      user = userService.findById(id);
    }

    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    model.addAttribute("departments", departmentService.findAll());
    return "user/edit";
  }

  @Secured(value = "ROLE_MANAGE_USER")
  @PostMapping("/delete")
  public String userDelete(@RequestParam("ids") List<Long> ids) {
    userService.deleteAllByIds(ids);
    return "redirect:/user/list";
  }

  @Secured(value = "ROLE_MANAGE_USER")
  @PostMapping("/save")
  public String userSave(Model model, @Valid @ModelAttribute("user") User user,
      BindingResult result) throws DataNotFoundException {
    if (user.hasPasswordChanged()) {
      if (!user.hasValidPassword()) {
        result.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      } else {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      }
    } else {
      User savedUser = userService.findById(user.getId());
      user.setPassword(savedUser.getPassword());
    }

    if (!result.hasErrors()) {
      userService.save(user);
      return "redirect:/user/list";
    }
    model.addAttribute("roles", Role.values());
    model.addAttribute("departments", departmentService.findAll());
    return "user/edit";
  }
}
