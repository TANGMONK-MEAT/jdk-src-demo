package test.treadUnsafe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 22:10
 */
public class ThreadLocalTest {

    public static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL = new InheritableThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };



    public static void main(String[] args) {

    }

}
