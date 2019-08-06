package kr.co.userinsight.edu.starter.service;

import java.util.List;
import kr.co.userinsight.edu.starter.domain.Department;
import kr.co.userinsight.edu.starter.domain.User;
import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.repository.DepartmentRepository;
import kr.co.userinsight.edu.starter.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  @NonNull
  private final DepartmentRepository departmentRepository;

  public Department findById(Long id) throws DataNotFoundException {
    return departmentRepository.findById(id).orElseThrow(DataNotFoundException::new);
  }

  public Page<Department> findAll(Pageable pageable) {
    return departmentRepository.findAll(pageable);
  }

  public List<Department> findAll() {
    return departmentRepository.findAll();
  }

  @Transactional
  public Department save(Department department) {
    return departmentRepository.save(department);
  }

  @Transactional
  public void deleteAllByIds(List<Long> ids) {
    List<Department> departmentList = departmentRepository.findAllByIdIn(ids);
    departmentRepository.deleteAll(departmentList);
  }

}
