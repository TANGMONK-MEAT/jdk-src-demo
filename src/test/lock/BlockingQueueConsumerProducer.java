package test.lock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/15 17:14
 */
public class BlockingQueueConsumerProducer {

    public static void main(String[] args) {
        // 资源
        Data data = new Data();
        // 生产者
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        },"A").start();
        // 消费者
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        },"C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        },"D").start();
    }



    static class Data{

        // 阻塞队列
        private final BlockingQueue<Integer> resourceQueue = new LinkedBlockingQueue<>(3);

        public void increment(){
            try {
                // 1 代表资源
                // 添加一个元素，如果队列满了，则阻塞
                resourceQueue.put(1);
                System.out.println(Thread.currentThread().getName() + " "
                        + resourceQueue.size() +
                        " +");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void decrement(){
            try {
                // 移除并返回队列头部的元素，如果队列为空则阻塞
                resourceQueue.take();
                System.out.println(Thread.currentThread().getName() + " " +
                        + resourceQueue.size() + " -");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
