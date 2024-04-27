package com.luke.fcmanagement.model.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String method;
    private String path;
    private Object resBody;
    private Integer status;
}