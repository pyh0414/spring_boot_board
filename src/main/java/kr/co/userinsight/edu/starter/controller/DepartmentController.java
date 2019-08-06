package kr.co.userinsight.edu.starter.controller;

import java.util.List;
import javax.validation.Valid;
import kr.co.userinsight.edu.starter.domain.Department;
import kr.co.userinsight.edu.starter.domain.User;
import kr.co.userinsight.edu.starter.enumerate.Role;
import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.service.DepartmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/department")
public class DepartmentController {
  @NonNull
  private final DepartmentService departmentService;


  @Secured(value = "ROLE_MANAGE_DEPARTMENT")
  @GetMapping("/list")
  public String departmentList(Model model,
      @SortDefault(value = "id", direction = Direction.ASC) Pageable pageable) {
    Page<Department> departments = departmentService.findAll(pageable);
    model.addAttribute("departments", departments);
    return "department/list";
  }


  @Secured(value = "ROLE_MANAGE_DEPARTMENT")
  @GetMapping(value = {"/edit", "/edit/{id}"})
  public String departmentEdit(Model model, @PathVariable(name = "id", required = false) Long id)
      throws DataNotFoundException {

    Department department = new Department();
    if (id != null) {
      department = departmentService.findById(id);
    }

    model.addAttribute("department", department);
    return "department/edit";
  }

  @Secured(value = "ROLE_MANAGE_DEPARTMENT")
  @PostMapping("/delete")
  public String departmentDelete(@RequestParam("ids") List<Long> ids) {
    departmentService.deleteAllByIds(ids);
    return "redirect:/department/list";
  }

  @Secured(value = "ROLE_MANAGE_DEPARTMENT")
  @PostMapping("/save")
  public String departmentSave(Model model, @Valid @ModelAttribute("department") Department department,
      BindingResult result) throws DataNotFoundException {

    if (!result.hasErrors()) {
      departmentService.save(department);
      return "redirect:/department/list";
    }

    return "department/edit";
  }
}
