package test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 * 模拟 Lock 和 Condition 实现精准唤醒, 其实还要一个标志位
 * @author zwl
 * @version 1.0
 * @date 2021/2/14 17:49
 */
public class Lock_condition_await_signal {

    public static void main(String[] args) {

        Data data = new Data();

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
        // 公平锁，但是，可能会出现即饥饿现象
        private final ReentrantLock lock = new ReentrantLock(true);
        // lock 监视器
        private final Condition condition1 = lock.newCondition();
        private final Condition condition2 = lock.newCondition();
        private final Condition condition3 = lock.newCondition();
        // 标志位
        private int number = 1;

        public void printA(){
            lock.lock();
            try {
                while (number != 1){
                    condition1.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] A");
                // 标志位设置为 2，代表 Thread2 符合运行条件
                number = 2;
                // 通唤醒Thread2
                condition2.signal();
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
                    condition2.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] B");
                // 标志位设置为 2，代表 Thread2 符合运行条件
                number = 3;
                // 通唤醒Thread2
                condition3.signal();
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
                    condition3.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] C");
                // 标志位设置为 2，代表 Thread2 符合运行条件
                number = 1;
                // 通唤醒Thread2
                condition1.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

}
