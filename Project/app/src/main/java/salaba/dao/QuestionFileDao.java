package salaba.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import salaba.vo.QuestionFile;
@Mapper
public interface QuestionFileDao {
  void add(QuestionFile file);

  int addAll(List<QuestionFile> files);

  List<QuestionFile> findAllByQuestionNo(int QuestionNo);

  QuestionFile findByNo(int no);
}
