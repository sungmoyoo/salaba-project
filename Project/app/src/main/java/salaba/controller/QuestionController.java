package salaba.controller;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.multipart.MultipartFile;
import salaba.service.MemberService;
import salaba.service.QuestionService;
import salaba.service.StorageService;
import salaba.vo.Member;
import salaba.vo.Nation;
import salaba.vo.Qna;
import salaba.vo.Question;
import salaba.vo.QuestionFile;

@RequiredArgsConstructor
@Controller
public class QuestionController {

  private static final Log log = LogFactory.getLog(QuestionController.class);
  private final QuestionService questionService;
  private final StorageService storageService;
  private String uploadDir = "question/";

  @Value("${ncpbucketname}")
  private String bucketName;

  @PostMapping("/member/questionAdd")
  public String questionAdd(
      Question question,
      MultipartFile[] questionFiles) throws Exception {

    List<QuestionFile> questionFileList = new ArrayList<>();
    int questionNo = questionService.getQuestionNo();
    question.setQuestionNo(questionNo);
    if (questionFiles != null && questionFiles.length > 0) {
      log.debug(String.format("로그로그 question No : %s", questionNo));
      for (MultipartFile file: questionFiles) {
        if (file.getSize() == 0) {
          continue;
        }
        log.debug(String.format("로그로그 file : %s", file.toString()));
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        questionFileList.add(QuestionFile.builder().questionNo(questionNo).uuidFileName(filename).oriFileName(file.getOriginalFilename()).build());
      }
    }

    questionService.questionAdd(question, questionFileList);
    return "redirect:/member/contact";
  }

  @GetMapping("/member/question")
  public void questionList(
      Model model,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    model.addAttribute("questionList", questionService.selectQuestionList(sessionInfo.getNo()));
  }

  @GetMapping("/member/contact")
  public String contact(HttpSession session){
    if(session.getAttribute("loginUser") == null){
      return "main";
    }
    return "/member/contact";
  }

}
