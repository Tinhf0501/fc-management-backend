package com.luke.fcmanagement.model.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {
    private String method;
    private String path;
    private Map<String, String> params;
    private List<Object> reqBody;
}
