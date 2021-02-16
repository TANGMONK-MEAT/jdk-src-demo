package test.java8.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 15:58
 */
public class DateTimeFormatterTest {

    // JDK1.8 DateTimeFormatter —— 解析和格式化日期或时间的类

    public static void main(String[] args) {

        // 指定格式 静态方法 ofPattern()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // DateTimeFormatter 自带的格式方法
        LocalDateTime now = LocalDateTime.now();

        // DateTimeFormatter 把日期对象，格式化成字符串
        String strDate1 = formatter.format(now);
        System.out.println(strDate1);

        // LocalDateTime 自带的格式化方法
        String strDate2 = now.format(formatter);
        System.out.println(strDate2);

    }

}
