package test.threadPool;

import java.util.concurrent.*;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/11 20:22
 */
public class KeepLiveTest {

    // 测试:
    // 设置 线程池 核心线程数=1，最大线程数=1，空闲线程最大存活时间=0（立即终止），阻塞队列长度=1
    // 按循序，立即提交三个任务，依次分别命名为 1，2，3
    // 任务处理时间 3s
    //
    // 执行步骤：
    // 任务1 首先提交，立即创建线程 ，执行任务1；
    // 任务2 提交时，任务1还未完成，于是，将任务2，保存到 阻塞队列中
    // 任务3 提交，任务1，未完成，任务2 还在阻塞队列中等待，
    //              因为此时已经达到核心线程数，并且阻塞队列已经满了
    //              还没有达到最大线程数，因此，创建新线程执行任务3
    // 任务 1 首先结束
    // 然后，任务3 结束，因为 keepLive=0，所以，执行完任务 3 的线程，将会立即被终止
    // 最后，执行完任务 1 的线程空闲，但是默认核心线程不会超时终止，所以，执行完任务 1 的线程继续继续执行任务 2
    // 任务 2 结束
    // 线程池 shutdown


    public static void main(String[] args) {
        ThreadPoolExecutor pool = null;

        try {
            // 实例化线程池
            pool = new ThreadPoolExecutor(1,2,
                                    0L, TimeUnit.MILLISECONDS,
                                            new ArrayBlockingQueue<>(1));

            // 未提交任何任务的线程池的状态
            System.out.println("Core threads: " + pool.getCorePoolSize());
            System.out.println("Largest executions: "
                    + pool.getLargestPoolSize());
            System.out.println("Maximum allowed threads: "
                    + pool.getMaximumPoolSize());
            System.out.println("Current threads in pool: "
                    + pool.getPoolSize());
            System.out.println("Currently executing threads: "
                    + pool.getActiveCount());
            System.out.println("Total number of threads(ever scheduled): "
                    + pool.getTaskCount());

            pool.execute(new Task("1"));
            pool.execute(new Task("2"));
            pool.execute(new Task("3"));

            System.out.println("========================================");
            // 阻塞 9s，线程池所有任务结束，阻塞 1s，看看核心线程和”借来“的线程的状态
            TimeUnit.SECONDS.sleep(10);
            System.out.println("========================================");

            // 执行完所有任务的线程池的状态
            System.out.println("Core threads: " + pool.getCorePoolSize());
            System.out.println("Largest executions: "
                    + pool.getLargestPoolSize());
            System.out.println("Maximum allowed threads: "
                    + pool.getMaximumPoolSize());
            System.out.println("Current threads in pool: "
                    + pool.getPoolSize());
            System.out.println("Currently executing threads: "
                    + pool.getActiveCount());
            System.out.println("Total number of threads(ever scheduled): "
                    + pool.getTaskCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 等待所有任务结束，才会关闭线程池
            assert pool != null;
            pool.shutdown();
        }


    }

    static class Task implements Runnable {

        private String name;

        public Task(String name){
            this.name = name;
        }

        public void run() {
            try {
                System.out.println(name + " Task running! [" + Thread.currentThread().getName() + "]");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + " Task Completed! ["+ Thread.currentThread().getName() + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
