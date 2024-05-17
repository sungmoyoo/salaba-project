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
  public void questionAdd(Question question, List<QuestionFile> questionFileList) {
    questionDao.questionAdd(question);
    if (questionFileList != null && questionFileList.size() > 0) {
      questionFileDao.addAll(questionFileList);
    }
  }

  @Override
  public List<Question> selectQuestionList(int memberNo) {
    return questionDao.selectQuestionList(memberNo);
  }

  @Override
  public int getQuestionNo() {
    return questionDao.getQuestionNo();
  }
}