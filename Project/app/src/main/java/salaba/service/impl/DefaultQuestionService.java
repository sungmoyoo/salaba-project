package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import salaba.dao.QuestionDao;
import salaba.service.QuestionService;
import salaba.vo.Qna;
import salaba.vo.Question;

@RequiredArgsConstructor
@Service
public class DefaultQuestionService implements QuestionService {

  private final QuestionDao questionDao;

  @Override
  public void questionAdd(Question question) {
    questionDao.questionAdd(question);
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

  @Override
  public int questionUpdate(Question question) {
    return questionDao.questionUpdate(question);
  }

}