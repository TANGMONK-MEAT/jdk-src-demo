package test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 * 使用同步锁，循环打印 10个a，5个b，3个c
 * @author zwl
 * @version 1.0
 * @date 2021/2/13 18:09
 */
public class Lock_condition {

    public static void main(String[] args) {

        Data data = new Data();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        }, "D").start();
    }

    static class Data{
        // 公平锁，类似于队列，默认 =false 是非公平锁
        private final ReentrantLock lock = new ReentrantLock(true);
        // 资源监视器
        private Condition condition = lock.newCondition();
        // 当前资源数
        private int count = 0;
        // 仓库资源的最大数量
        private final int MAX_COUNT = 3;

        // ++; 生产资源
        public void increment(){
            lock.lock();
            try {
                while (count + 1 > MAX_COUNT){
                    // 无限等待，直到 被唤醒
                    condition.await();
                }
                count++;
                System.out.println("[" + Thread.currentThread().getName() +"] +1 " + count);
                // 唤醒所有
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        // --；消费资源
        public void decrement(){
            lock.lock();
            try {
                while (count - 1 < 0){
                    // 无限等待，直到 被唤醒
                    condition.await();
                }
                count--;
                System.out.println("[" + Thread.currentThread().getName() +"] -1 " + count);
                // 唤醒所有
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
