package kr.co.userinsight.edu.starter.repository;

import kr.co.userinsight.edu.starter.domain.FreeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long>,
    PagingAndSortingRepository<FreeBoard, Long> {

}
