package salaba.service;

import java.util.List;
import salaba.vo.Qna;
import salaba.vo.Question;
import salaba.vo.QuestionFile;

public interface QuestionService {

  void questionAdd(Question question, List<QuestionFile> questionFileList);

  List<Question> selectQuestionList(int memberNo);

//  Question get(int questionNo);

  int getQuestionNo();

}
