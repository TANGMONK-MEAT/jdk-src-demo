package test.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 14:57
 */
public class CachedThreadPoolTest {

    // CachedThreadPool 线程池：
    // 构造方法：
    // new ThreadPoolExecutor(0, Integer.MAX_VALUE,
    //                          60L, TimeUnit.SECONDS,
    //                          new SynchronousQueue<Runnable>());
    // 任务执行的流程：
    // 1 因为没有核心线程，所以，会直接向 SynchronousQueue 中提交任务；
    // 2 如果线程池中有空闲线程，就去取出任务执行；如果没有空闲线程，就新建一个；
    // 3 执行完任务的线程有 60 秒生存时间，如果在这个时间内可以接到新任务，就可以继续活下去，否则就拜拜；
    // 4 由于空闲 60 秒的线程会被终止，长时间保持空闲的 CachedThreadPool 不会占用任何资源。

    // SynchronousQueue 阻塞队列：
    // CachedThreadPool 使用的阻塞队列是 SynchronousQueue。
    // SynchronousQueue 是一个内部只能包含一个元素的队列。
    // 插入元素到队列的线程被阻塞，直到另一个线程从队列中获取了队列中存储的元素。
    // 同样，如果线程尝试获取元素并且当前不存在任何元素，则该线程将被阻塞，直到线程将元素插入队列。

    // 用途：
    // CachedThreadPool 用于并发执行大量短期的小任务，或者是负载较轻的服务器。


     // 在Executors类里面提供了一些静态工厂，生成一些常用的线程池。
     // newCachedThreadPool
     //
     // 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，
     // 当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
     // 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。

    // OOM 产生的原因：
    // 如果每个线程的任务的处理时间都比较长，那么，每次新添加的任务都会新创建线程去处理。
    // 如果 同时有 几万个线程在同时运行，对服务器的压力是巨大的，很可能会造成堆内存溢出。


    public static void main(String[] args) {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        try {
            // // 测试0：OOM 再现
            // // 开启 100000个线程，同时执行
            // for (int i = 0; i < 100000; i++) {
            //     pool.execute(() ->{
            //         try {
            //             TimeUnit.SECONDS.sleep(5);
            //             System.out.println("[" + Thread.currentThread().getName() + "], 正在执行。。。");
            //         } catch (InterruptedException e) {
            //             e.printStackTrace();
            //         }
            //     });
            // }

            // 测试1：
            // 线程的一个任务结束后，有60s的存活时间，如果在 存活时间内有新任务，就会继续执行。
            pool.execute(new MyThread2("线程实例" + 2));
            TimeUnit.SECONDS.sleep(2);
            pool.execute(new MyThread("线程实例" + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            pool.shutdown();
        }
    }

    static class MyThread2 extends Thread {
        public MyThread2(String name){
            super(name);
        }
        @Override
        public void run() {
            try {
                System.out.println(this.getName() + "[" + Thread.currentThread().getName() + "], 正在执行。。。");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class MyThread extends Thread {
        public MyThread(String name){
            super(name);
        }
        @Override
        public void run() {
            try {
                System.out.println(this.getName() + "[" + Thread.currentThread().getName() + "], 正在执行。。。");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
