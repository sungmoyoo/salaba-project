package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Qna;
import salaba.vo.Question;

@Mapper
public interface QuestionDao {

  void questionAdd(Question question);

  List<Question> findAll(Question question);

  Question findBy(int questionNo);

  Qna getAnswer(int questionNo);

  int questionUpdate(Question question);

}
