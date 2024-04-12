package salaba.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import salaba.dao.Point_historyDao;
import salaba.dao.QuestionDao;
import salaba.service.QuestionService;
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
  public Question get(int no) {
    return questionDao.findBy(no);
  }

  @Override
  public int questionUpdate(Question question) {
    return questionDao.questionUpdate(question);
  }
}