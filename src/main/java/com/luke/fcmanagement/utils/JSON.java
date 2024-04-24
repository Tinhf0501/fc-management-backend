package com.luke.fcmanagement.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class JSON {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String stringify(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }
}
