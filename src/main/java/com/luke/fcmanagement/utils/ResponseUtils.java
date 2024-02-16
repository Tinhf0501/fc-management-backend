package com.luke.fcmanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Component
@Slf4j
public class ResponseUtils {
    private static SpringValidatorAdapter springValidatorAdapter;

    public ResponseUtils(SpringValidatorAdapter springValidatorAdapter) {
        ResponseUtils.springValidatorAdapter = springValidatorAdapter;
    }

    public static boolean isInvalidObject(Object object) {
        DirectFieldBindingResult directFieldBindingResult = new DirectFieldBindingResult(object, "USER");
        springValidatorAdapter.validate(object, directFieldBindingResult);
        if (directFieldBindingResult.hasErrors()) {
            return true;
        }
        return false;
    }

}
