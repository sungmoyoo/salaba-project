package org.admin.repository;

import org.admin.domain.Qna;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {
    List<Qna> findAllQ();

}
