package com.luke.fcmanagement.model.log;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Request {
    private String method;
    private String path;
    private Map<String, String[]> params;
    private List<Object> reqBody;
}
