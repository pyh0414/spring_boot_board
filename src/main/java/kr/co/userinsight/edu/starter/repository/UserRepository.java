package kr.co.userinsight.edu.starter.repository;

import java.util.List;
import kr.co.userinsight.edu.starter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

  User findByUsername(String username);

  List<User> findAllByIdIn(List<Long> ids);
}
