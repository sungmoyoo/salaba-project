package org.admin.domain;

import lombok.Data;

@Data
public class Photo {
    private int no;
    private String originalName;
    private String uuidName;
    private String explanation;
    private int order;
}
