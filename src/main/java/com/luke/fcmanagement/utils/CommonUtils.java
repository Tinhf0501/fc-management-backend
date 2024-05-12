package com.luke.fcmanagement.utils;

import com.luke.fcmanagement.constants.ErrorCode;
import com.luke.fcmanagement.exception.BusinessException;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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

    public Pageable createPageable(Integer pageNo, Integer pageSize) {
        if (Objects.isNull(pageNo) || Objects.isNull(pageSize))
            throw new BusinessException(ErrorCode.VALIDATE_FAIL);
        return PageRequest.of(pageNo - 1, pageSize);
    }

    public static Date getDateWith00h00(Date dateInput) {
        if (Objects.isNull(dateInput))
            return null;
        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        cal.setTime(dateInput);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        // Bo sung
        cal.set(Calendar.MILLISECOND, 000);
        return cal.getTime();
    }

    public static Date getDateWith23h59(Date dateInput) {
        if (Objects.isNull(dateInput))
            return null;
        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        cal.setTime(dateInput);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        // Bo sung
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
