package test.treadUnsafe;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 18:17
 */
public class SimpleDateFormatTest2 {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 初始化每个线程的时间
    private static List<Date> list = new ArrayList<>();

    static {
        Date date1 = new Date(2011-1900, Calendar.JANUARY,1);
        Date date2 = new Date(2012-1900, Calendar.JANUARY,1);
        Date date3 = new Date(2013-1900, Calendar.JANUARY,1);
        Date date4 = new Date(2014-1900, Calendar.JANUARY,1);
        Date date5 = new Date(2015-1900, Calendar.JANUARY,1);
        Date date6 = new Date(2016-1900, Calendar.JANUARY,1);
        Date date7 = new Date(2017-1900, Calendar.JANUARY,1);
        Date date8 = new Date(2018-1900, Calendar.JANUARY,1);
        Date date9 = new Date(2019-1900, Calendar.JANUARY,1);
        Date date10 = new Date(2020-1900, Calendar.JANUARY,1);
        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
        list.add(date5);
        list.add(date6);
        list.add(date7);
        list.add(date8);
        list.add(date9);
        list.add(date10);

    }

    public static void main(String[] args) {

        // FixedThreadPool 线程池：
        // 构造方法：
        // new ThreadPoolExecutor(nThreads, nThreads,
        //                                       0L, TimeUnit.MILLISECONDS,
        //                                       new LinkedBlockingQueue<Runnable>());
        // 可以看到，核心线程数 == 最大线程数，且 KeepLive == 0，
        // KeepLive： 表示当核心线程满了（肯定没有超过最大线程数），新创建的线程，在没有执行任务的期间可以存活的最大时间
        // KeepLive == 0，则表示不等待直接退出
        // 工厂类传递的第一个参数和第二个参数都设置成了nThreads。即线程池的核心线程数和最大线程数相等。

        // FixedThreadPool 线程池的工作流程：
        // 1 如果当前运行的线程数量小于corePoolSize，则创建新线程来执行任务。
        // 2 在线程池中当前运行的线程数量等于corePoolSize，由于无法在创建新的线程进行任务处理，所以会将任务加入到阻塞队列中进行排队等候处理。
        // 3 当线程池中的线程执行完一个任务后，就会去阻塞队列中循环获取新的任务继续执行。

        // 无边界阻塞队列：LinkedBlockingQueue
        // 构造方法：
        // public LinkedBlockingQueue() {
        //     this(Integer.MAX_VALUE);
        // }
        // 通过构造方法可以看到，这是一个 大小为 Integer.MAX_VALUE 的阻塞队列

        // 注意：因为LinkedBlockingQueue是长度为Integer.MAX_VALUE的队列，可以认为是无界队列，
        //      因此往队列中可以插入无限多的任务，在资源有限的时候容易引起OOM异常
        ExecutorService service = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                final int t = i;
                service.execute(() -> {
                    Date date = list.get(t % 10);
                    // 格式化 日期
                    System.out.println("[" + Thread.currentThread().getName() + "]: 格式化前" + date);
                    String res = sdf.format(list.get(t % 5));
                    System.out.println("[" + Thread.currentThread().getName() + "]: 格式化后" + res);
                });
            }
        } finally {
            // 等待上述的线程全部执行完,再关闭线程池
            service.shutdown();
        }
    }
}
