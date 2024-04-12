package org.admin.util;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


public final class Translator {
    public static final Map<String, String> dealState = new HashMap<>();

    public static final Map<String, String> rentalState = new HashMap<>();
    public static final Map<String, String> memberState = new HashMap<>();
    public static final Map<String, String> textState = new HashMap<>();

    static {
        dealState.put("0", "미완료");
        dealState.put("1", "완료");

        rentalState.put("0","등록대기");
        rentalState.put("1","운영중");
        rentalState.put("2","중지");
        rentalState.put("3","삭제");
        rentalState.put("4","등로거부");
        rentalState.put("5","제재");

        memberState.put("0", "일반");
        memberState.put("1", "탈퇴");
        memberState.put("2", "제재");
        memberState.put("3", "휴면");

        textState.put("0", "일반");
        textState.put("1", "삭제");
        textState.put("2", "제재");
    }

    private Translator() {}


}
