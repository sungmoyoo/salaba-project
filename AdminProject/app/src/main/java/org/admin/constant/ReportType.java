package org.admin.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    BOARD('0'),
    COMMENT('1'),
    REPLY('2');

    private final char value;


}
