package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.QuestionDao;
import salaba.dao.QuestionFileDao;
import salaba.service.QuestionService;
import salaba.vo.Qna;
import salaba.vo.Question;
import salaba.vo.QuestionFile;

@RequiredArgsConstructor
@Service
public class DefaultQuestionService implements QuestionService {

  private final QuestionDao questionDao;
  private final QuestionFileDao questionFileDao;

  @Transactional
  @Override
  public void questionAdd(Question question) {
    int questionNo = questionDao.getQuestionNo();

    question.setQuestionNo(questionNo);
    questionDao.questionAdd(question);
    if (question.getQuestionFileList() != null && question.getQuestionFileList().size() > 0) {
      for (QuestionFile questionFile : question.getQuestionFileList()) {
        questionFile.setQuestionNo(question.getQuestionNo());
      }
      questionFileDao.addAll(question.getQuestionFileList());
    }
  }

  @Override
  public List<Question> questionList(Question question) {
    return questionDao.findAll(question);
  }

  @Override
  public Question get(int questionNo) {
    return questionDao.findBy(questionNo);
  }

  @Override
  public Qna getAnswer(int questionNo) {
    return questionDao.getAnswer(questionNo);
  }

  @Transactional
  @Override
  public int questionUpdate(Question question) {
    if (question.getQuestionFileList() != null && question.getQuestionFileList().size() > 0) {
      for (QuestionFile questionFile : question.getQuestionFileList()) {
        questionFile.setQuestionNo(question.getQuestionNo());
      }
      questionFileDao.addAll(question.getQuestionFileList());
    }
    return questionDao.questionUpdate(question);
  }

  @Override
  public List<QuestionFile> getQuestionFiles(int no) {
    return questionFileDao.findAllByQuestionNo(no);
  }

  @Override
  public QuestionFile getQuestionFile(int fileNo) {
    return questionFileDao.findByNo(fileNo);
  }


}