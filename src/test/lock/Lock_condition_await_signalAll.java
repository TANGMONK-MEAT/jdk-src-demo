package test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 * 模拟用户购物：循环 支付->下单->物流
 * @author zwl
 * @version 1.0
 * @date 2021/2/14 15:53
 */
public class Lock_condition_await_signalAll {

    // 注意：
    // ReentraantLock 是通过一个FIFO的等待队列来管理获取该锁所有线程的。在“公平锁”的机制下，线程依次排队获取锁；
    // 而“非公平锁”在锁是可获取状态时，不管自己是不是在队列的开头都可能获取锁。

    // 由于 Condition#signalAll 是唤醒所有等待的线程，谁先获取锁，谁就可以running
    // 所以，为了避免 "虚假唤醒"，即，A 结束后，会唤醒 B 和 C ，但是，理应让B 执行，C继续阻塞，所以，B 和 C都要判断这次的唤醒是否符合规则。
    // 既然要判断多次，给个 循环就可以；
    // 因此，设置标志位，标志位初始为 1，代表 A可以执行，A执行后 设置 标志位 为 2，代表 B可以执行。。。以此类推

    public static void main(String[] args) {

        Data data = new Data();

        // new Thread(data::printA, "A").start();
        // new Thread(data::printB, "B").start();
        // new Thread(data::printC, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printC();
            }
        }, "C").start();


    }

    static class Data{
        // 公平锁，默认是false 非公平锁
        private final ReentrantLock lock = new ReentrantLock(true);
        private final Condition condition = lock.newCondition();
        private int number = 1;

        public void printA(){
            lock.lock();
            try {
                while (number != 1){
                    condition.await();
                }
                System.out.println("[" + Thread.currentThread().getName() +"] 支付");
                number = 2;
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public void printB(){
            lock.lock();
            try {
                while (number != 2){
                    condition.await();
                }
                System.out.println("[" + Thread.currentThread().getName() +"] 下单");
                number = 3;
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public void printC(){
            lock.lock();
            try {
                while (number != 3){
                    condition.await();
                }
                System.out.println("[" + Thread.currentThread().getName() +"] 物流");
                number = 1;
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
