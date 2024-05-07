package com.luke.fcmanagement.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@UtilityClass
public class CommonUtils {
    public String getRequestUri() {
        String requestUri = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRequestURI();
        return requestUri;
    }

    public String createSLug(String str) {
        // Chuyển hết sang chữ thường
        str = str.toLowerCase();
        // xóa dấu
        str = str.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        str = str.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        str = str.replaceAll("[ìíịỉĩ]", "i");
        str = str.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        str = str.replaceAll("[ùúụủũưừứựửữ]", "u");
        str = str.replaceAll("[ỳýỵỷỹ]", "y");
        str = str.replaceAll("[đ]", "d");

        // Xóa ký tự đặc biệt
        str = str.replaceAll("[^0-9a-z\\s-]", "");

        // Xóa khoảng trắng thay bằng ký tự -
        str = str.replaceAll("\\s+", "-");

        // Xóa ký tự - liên tiếp
        str = str.replaceAll("-+", "-");

        // xóa phần dư - ở đầu
        str = str.replaceAll("^-+", "");

        // xóa phần dư - ở cuối
        str = str.replaceAll("-+$", "");

        // return
        return str;
    }
}
