package test.java8.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 15:36
 */
public class DateTest {

    public static void main(String[] args) {

        // 使用JDK8全新的日期和时间API: LocalDate、LocalTime、LocalDateTime

        // LocalDateTime：获取年月日时分秒等于 LocalDate + LocalTime
        // 例如：2021-02-11T15:39:04.461
        LocalDateTime localDateTime = LocalDateTime.now();
        // 2021-02-11T15:39:04.461
        System.out.println(localDateTime);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        // 2021-02-11 15:39:04.461
        System.out.println(localDate + " " + localTime );

        // LocalDate：获取年月日
        LocalDate localDate1 = LocalDate.now();
        // 2021-02-11
        System.out.println(localDate1);

        // LocalTime：获取时分秒
        LocalTime localTime1 = LocalTime.now();
        // 15:41:26.223
        System.out.println(localTime1);

    }

}
