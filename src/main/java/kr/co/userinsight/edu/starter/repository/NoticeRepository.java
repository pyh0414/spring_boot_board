package kr.co.userinsight.edu.starter.repository;

import kr.co.userinsight.edu.starter.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>,
    PagingAndSortingRepository<Notice, Long> {


}
