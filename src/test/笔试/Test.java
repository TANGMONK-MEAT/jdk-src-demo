package test.笔试;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/14 21:43
 */
public class Test {

    public static void main(String[] args) {

        Data data = new Data();

        new Thread(() -> {
            for (int i = 0; i <= 5; i++) {
                data.printOs();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                data.printJs();
            }
        }, "B").start();
    }

    static class Data{

        // 使用公平锁
        private final ReentrantLock lock = new ReentrantLock(true);
        // lock 监视器
        private final Condition condition = lock.newCondition();
        // 需要打印的数
        private int number = 0;
        public void printOs(){
            lock.lock();
            try {
                while (number % 2 != 0){
                    condition.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] " + number);
                number++;
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public void printJs(){
            lock.lock();
            try {
                while (number % 2 == 0){
                    condition.await();
                }
                System.out.println("[" + Thread.currentThread().getName() + "] " + number);
                number++;
                condition.signalAll();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
