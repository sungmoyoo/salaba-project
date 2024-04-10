package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Qna;
import org.admin.repository.QnaDao;
import org.admin.service.QnaService;
import org.admin.util.Translator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultQnaService implements QnaService {

    private final QnaDao qnaDao;


    @Override
    public List<Qna> getAllQ() {
        List<Qna> qnas = qnaDao.findAllQ();
        for (Qna qna : qnas) {
            qna.setStateStr(Translator.dealState.get(qna.getState()));
        }
        return qnas;
    }

    @Override
    public Qna getBy(int qnaNo) {
        Qna qna = qnaDao.findBy(qnaNo);
        qna.setStateStr(Translator.dealState.get(qna.getState()));
        return qna;
    }
}
