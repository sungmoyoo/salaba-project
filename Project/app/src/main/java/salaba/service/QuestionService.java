package salaba.service;

import java.util.List;
import salaba.vo.Qna;
import salaba.vo.Question;
import salaba.vo.QuestionFile;

public interface QuestionService {

  void questionAdd(Question question);

  List<Question> questionList(Question question);

  Question get(int questionNo);

  Qna getAnswer(int questionNo);

  int questionUpdate(Question question);

  List<QuestionFile> getQuestionFiles(int no);

  QuestionFile getQuestionFile(int fileNo);
}
