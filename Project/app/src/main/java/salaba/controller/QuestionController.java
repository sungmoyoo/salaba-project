package salaba.controller;


import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import salaba.service.QuestionService;
import salaba.service.StorageService;
import salaba.vo.Question;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

  private static final Log log = LogFactory.getLog(QuestionController.class);
  private final QuestionService questionService;
  private final StorageService storageService;

  @GetMapping("questionForm")
  public void questionForm() throws Exception {
  }

  @PostMapping("questionAdd")
  public String questionAdd(
      Question question,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    questionService.questionAdd(question);
    question.setNo(sessionInfo.getNo());
    return "redirect:questionList";
  }

  @GetMapping("questionList")
  public void questionList(
      Question question,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    question.setNo(sessionInfo.getNo());

    model.addAttribute("question", questionService.questionList(question));
  }

  @GetMapping("questionView")
  public void questionView(int no, Model model) throws Exception {
    Question question = questionService.get(no);
    model.addAttribute("question", question);
  }

  @PostMapping("questionUpdate")
  public String questionUpdate(Question question) throws Exception {
    Question old = questionService.get(question.getNo());

    questionService.questionUpdate(question);
    return "redirect:questionView";
  }
}
