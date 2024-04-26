package salaba.service;

import java.util.List;
import salaba.vo.Qna;
import salaba.vo.Question;

public interface QuestionService {

  void questionAdd(Question question);

  List<Question> questionList(Question question);

  Question get(int questionNo);

  Qna getAnswer(int questionNo);

  int questionUpdate(Question question);

}
