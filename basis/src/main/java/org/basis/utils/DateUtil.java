package org.basis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String formatDate2String(Date date) {
        // 接收待返回的时间字符串
        String resultTimeStr = "";
        if(date != null) {
            try {
                SimpleDateFormat formatPattern = new SimpleDateFormat("HH:mm:ss");
                resultTimeStr = formatPattern.format(date);
            } catch (Exception e) {}
        }

        return resultTimeStr;
    }
}
