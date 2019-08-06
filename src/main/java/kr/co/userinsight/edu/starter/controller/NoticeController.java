package kr.co.userinsight.edu.starter.controller;

import kr.co.userinsight.edu.starter.domain.Notice;
import kr.co.userinsight.edu.starter.service.NoticeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

  @NonNull
  private final NoticeService noticeService;

  @Secured(value = "ROLE_MANAGE_NOTICE")
  @GetMapping("/list")
  public String noticeList(Model model,
      @SortDefault(value = "id", direction = Direction.ASC) Pageable pageable) {
    Page<Notice> notices = noticeService.findAll(pageable);
    model.addAttribute("notices", notices);
    return "notice/list";
  }

}
