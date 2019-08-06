package kr.co.userinsight.edu.starter.service;

import kr.co.userinsight.edu.starter.domain.Notice;
import kr.co.userinsight.edu.starter.repository.NoticeRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

  @NonNull
  private final NoticeRepository noticeRepository;

  public Page<Notice> findAll(Pageable pageable) {
    return noticeRepository.findAll(pageable);
  }

}
