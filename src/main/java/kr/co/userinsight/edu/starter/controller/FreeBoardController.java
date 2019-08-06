package kr.co.userinsight.edu.starter.controller;

import kr.co.userinsight.edu.starter.domain.FreeBoard;
import kr.co.userinsight.edu.starter.domain.User;
import kr.co.userinsight.edu.starter.exception.DataNotFoundException;
import kr.co.userinsight.edu.starter.service.FreeBoardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/freeboard")
public class FreeBoardController {

  @NonNull
  private final FreeBoardService freeBoardService;

  @Secured(value = "ROLE_MANAGE_FREEBOARD")
  @GetMapping("/list")
  public String freeBoardList(Model model,
      @SortDefault(value = "id", direction = Direction.ASC) Pageable pageable) {
    Page<FreeBoard> freeBoards = freeBoardService.findAll(pageable);
    model.addAttribute("freeBoards", freeBoards);
    return "freeboard/list";
  }

  @Secured(value = "ROLE_MANAGE_FREEBOARD")
  @GetMapping(value = {"/edit", "/edit/{id}"})
  public String freeboardEdit(Model model, @PathVariable(name = "id", required = false) Long id)
          throws DataNotFoundException {

    FreeBoard freeBoard = new FreeBoard();
    if (id != null) {
      freeBoard = freeBoardService.findById(id);
    }

    model.addAttribute("freeBoard", freeBoard);
    return "freeboard/edit";
  }

  @Secured(value = "ROLE_MANAGE_FREEBOARD")
  @PostMapping("/save")
  public String saveFreeBoard(Model model, @ModelAttribute("freeBoard") FreeBoard freeBoard)
          throws DataNotFoundException {
    freeBoard.setDate(new Date());
    freeBoard.setWriter((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    freeBoardService.save(freeBoard);
    return "redirect:/freeboard/list";
  }



}
