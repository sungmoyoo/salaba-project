package salaba.service;

import java.util.List;
import salaba.vo.Question;

public interface QuestionService {

  void questionAdd(Question question);

  List<Question> questionList(Question question);

  Question get(int no);

  int questionUpdate(Question question);

}
