package kr.co.userinsight.edu.starter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class MainController {

  //  @RequestMapping(value="/", method = RequestMethod.GET)
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("text", "안녕하세요.");
    return "index";
  }
}
