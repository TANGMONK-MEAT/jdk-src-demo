package test.java8.date;

import java.time.Instant;
import java.util.Date;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 15:54
 */
public class InstantTest {

    // JDK8 获取时间戳

    public static void main(String[] args) {

        //从1970 - 01 - 01 00:00:00 截止到当前时间的毫秒值
        long l0 = System.currentTimeMillis();
        System.out.println(l0);
        long time = new Date().getTime();
        System.out.println(time);


        //JDK1.8 Instant 时间戳类从1970 -01 - 01 00:00:00 截止到当前时间的毫秒值
        Instant now1 = Instant.now();

        //toEpochMilli():从1970 -01 - 01 00:00:00 截止到当前时间间隔的毫秒值
        long l1 = now1.toEpochMilli();
        System.out.println(l1);

        //获取从1970 -01 - 01 00:00:00 截止到当前时间间隔的秒值
        long epochSecond = now1.getEpochSecond();
        System.out.println(epochSecond);
    }
}
