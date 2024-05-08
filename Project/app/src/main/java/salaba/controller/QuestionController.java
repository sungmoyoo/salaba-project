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
@RequestMapping("/question")
public class QuestionController {

  private static final Log log = LogFactory.getLog(QuestionController.class);
  private final QuestionService questionService;
  private final StorageService storageService;
  private final MemberService memberService;
  private String uploadDir = "question/";

  @Value("${ncpbucketname}")
  private String bucketName;

  @PostMapping("questionAdd")
  public String questionAdd(
      Question question,
      MultipartFile[] questionFiles,
      HttpSession session) throws Exception {

    Member sessionInfo = (Member) session.getAttribute("loginUser");
    question.setNo(sessionInfo.getNo());

    System.out.println("questionFiles :"+questionFiles.length);

    ArrayList<QuestionFile> questionFileList = new ArrayList<>();
    if (questionFiles != null && questionFiles.length > 0) {
      for (MultipartFile file: questionFiles) {
        if (file.getSize() == 0) {
          throw new Exception("첨부파일을 등록하세요!");
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        questionFileList.add(QuestionFile.builder().uuidFileName(filename).oriFileName(file.getOriginalFilename()).build());
      }
    }

    if (questionFileList.size() > 0) {
      question.setQuestionFileList(questionFileList);
    }

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
    session.setAttribute("myInfoMenuId", question.getMyInfoMenuId());
  }

  @GetMapping("questionView")
  public void questionView(int questionNo, Model model) throws Exception {

    Question question = questionService.get(questionNo);
    model.addAttribute("question", question);
    Qna qna = questionService.getAnswer(questionNo);
    model.addAttribute("qna", qna);
  }
}
