package test.threadBasic;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/15 11:29
 */
public class JoinTest {

    public static void main(String[] args) {

        System.out.println("mainThread " + Thread.currentThread().getState().name());

        Thread childThread = new Thread(() -> {
            try {
                System.out.println("childThread " + Thread.currentThread().getState().name());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "childThread");

        // 启动子线程
        childThread.start();

        try {
            // childThread 加入 mainThread
            // 其实就是 mainThread 会获得 childThread 实例作为锁，因为 childThread.join(0) 是 synchronized 修饰的
            // mainThread 调用了 childThread.wait(0) 进入永久阻塞
            // 除非 childThread 结束（TERMINATED），释放所有资源，否则 mainThread 会一直 处于 TIMED_WAITING 状态
            // 线程结束后，会调用 notifyAll 唤醒所有的线程
            childThread.join();
            System.out.println("childThread " + childThread.getState().name());
            System.out.println("mainThread " + Thread.currentThread().getState().name());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
