package org.admin.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestResult {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private Object data;
    private String error;


}
