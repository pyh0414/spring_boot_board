package kr.co.userinsight.edu.starter.service;

import java.util.List;
import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.domain.User;
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
public class UserService implements UserDetailsService {

  @NonNull
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

  public Page<User> findAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  public User findById(Long id) throws DataNotFoundException {
    return userRepository.findById(id).orElseThrow(DataNotFoundException::new);
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  @Transactional
  public void deleteAllByIds(List<Long> ids) {
    List<User> userList = userRepository.findAllByIdIn(ids);
    userRepository.deleteAll(userList);
  }

}
