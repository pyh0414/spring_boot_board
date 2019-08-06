package kr.co.userinsight.edu.starter.service;

import kr.co.userinsight.edu.starter.domain.FreeBoard;
import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.repository.FreeBoardRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FreeBoardService {

  @NonNull
  private final FreeBoardRepository freeBoardRepository;

  public Page<FreeBoard> findAll(Pageable pageable) {
    return freeBoardRepository.findAll(pageable);
  }


  public FreeBoard findById(Long id) throws DataNotFoundException {
    return freeBoardRepository.findById(id).orElseThrow(DataNotFoundException::new);
  }

  @Transactional
  public FreeBoard save(FreeBoard freeBoard) {
    return freeBoardRepository.save(freeBoard);
  }


}
