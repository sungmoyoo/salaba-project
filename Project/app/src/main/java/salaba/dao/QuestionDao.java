package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import salaba.vo.Qna;
import salaba.vo.Question;

@Mapper
public interface QuestionDao {

  void questionAdd(Question question);

  List<Question> selectQuestionList(@Param("memberNo") int memberNo);

//  Question findBy(@Param("questionNo") int questionNo);

  int getQuestionNo();

}
