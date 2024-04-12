package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.Question;

@Mapper
public interface QuestionDao {

  void questionAdd(Question question);

  List<Question> findAll(Question question);

}
