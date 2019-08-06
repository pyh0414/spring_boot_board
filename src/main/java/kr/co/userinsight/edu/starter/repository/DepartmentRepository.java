package kr.co.userinsight.edu.starter.repository;

import java.util.List;
import kr.co.userinsight.edu.starter.domain.Department;
import kr.co.userinsight.edu.starter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
    extends JpaRepository<Department, Long>, PagingAndSortingRepository<Department, Long> {

  List<Department> findAllByIdIn(List<Long> ids);
}
