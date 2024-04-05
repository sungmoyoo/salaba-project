package org.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    //신고테이블의 신고 대상 타입
    BOARD('0'),
    COMMENT('1'),
    REPLY('2');

    private final char value;

}
