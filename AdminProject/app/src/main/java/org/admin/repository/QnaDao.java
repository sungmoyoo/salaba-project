package org.admin.repository;

import org.admin.domain.Qna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaDao {
    List<Qna> findAllQ();
    Qna findBy(@Param("questionNo") int questionNo);

    void addAnswer(Qna qna);

    int updateState(Qna qna);

}
