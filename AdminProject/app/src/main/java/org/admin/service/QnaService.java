package org.admin.service;

import org.admin.domain.Qna;

import java.util.List;

public interface QnaService {
    List<Qna> getAllQ();
    Qna getBy(int qnaNo);
}
