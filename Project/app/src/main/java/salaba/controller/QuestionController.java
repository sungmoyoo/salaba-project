package salaba.controller;


import java.util.List;
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
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.Qna;
import salaba.vo.Question;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

  private static final Log log = LogFactory.getLog(QuestionController.class);
  private final QuestionService questionService;
  private final StorageService storageService;

  @PostMapping("questionAdd")
  public String questionAdd(
      Question question,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    question.setNo(sessionInfo.getNo());
    questionService.questionAdd(question);
    return "redirect:questionList";
  }

  @GetMapping("questionList")
  public void questionList(
      Question question,
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    question.setNo(sessionInfo.getNo());

    model.addAttribute("questionList", questionService.questionList(question));
  }

  @GetMapping("questionView")
  public void questionView(int questionNo, Model model) throws Exception {

    Question question = questionService.get(questionNo);
    model.addAttribute("question", question);
    Qna qna = questionService.getAnswer(questionNo);
    model.addAttribute("qna", qna);
  }

  @PostMapping("questionUpdate")
  public String questionUpdate(Question question) throws Exception {
    Question old = questionService.get(question.getNo());

    questionService.questionUpdate(question);
    return "redirect:questionView";
  }

  
}
