package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Qna;
import org.admin.repository.QnaDao;
import org.admin.service.QnaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQnaService implements QnaService {

    private final QnaDao qnaDao;


    @Override
    public List<Qna> getAllQ() {
        return qnaDao.findAllQ();
    }
}
