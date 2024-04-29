package com.luke.fcmanagement.model;

import com.luke.fcmanagement.constants.FieldConstant;
import com.luke.fcmanagement.constants.Message;

import java.util.HashMap;

public class ApiBody extends HashMap<String, Object> {

    public void setMessage(Message message) {
        this.put(FieldConstant.MESSAGE, message);
    }
}
