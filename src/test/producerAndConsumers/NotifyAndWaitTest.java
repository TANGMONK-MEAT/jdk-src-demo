package test.producerAndConsumers;

/**
 * Description:
 * 使用 NotifyAll 和 wait 模拟生产者和消费者的问题
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/12 15:55
 */
public class NotifyAndWaitTest {

    // 模拟 一个资源的获取和释放

    // 步骤
    // 生产者：
    //      1 判断资源是否充裕；
    //          1 如果资源充裕，就没必要再生产了，等待消费者消费完资源为止
    //      2 如果资源不足，就必须立即生产资源
    //          1 资源生产完之后，必须通知消费者
    // 消费者：
    //      1 判断资源是否充裕；
    //          1 如果资源不足，就不能再消费了，等待生产者生产出资源为止
    //      2 如果资源充足
    //          1 直接消费，之后，再通知生产者
    //
    // 注意：
    // Object#notifyAll 方法可使所有正在等待队列中等待同一共享资源的“全部”线程从等待状态退出，进入可运行状态。
    // 此时，优先级最高的哪个线程最先执行，但也有可能是随机执行的，这要取决于JVM虚拟机的实现。
    // 即，最终也只有一个线程能被运行，上述线程优先级都相同，每次运行的线程都不确定是哪个，后来给线程设置优先级后也跟预期不一样，还是要看JVM的具体实现吧。
    //
    // 问题：
    // 1 唤醒线程的问题：
    //      1 有可能会出现极端情况，
    //          1 每次唤醒的都是生产者线程，消费者线程一直处于就绪状态，如果生产者不判断生产的必要性，那么，资源就会越积越多，超过仓库的容量。
    //          2 也可能，每次唤醒的都是消费者线程，生产者生产完第一个资源，就一直处于就绪状态，如果消费者不判断是否可以消费，那么，就会出现 资源负数
    //
    // 解决：
    // 每次被唤醒后，都判断是否应该被唤醒，否则，就再次进入阻塞状态
    // 1 方案一：（情况发生后，补救）
    //      1 生产者：每次被唤醒后，都判断（生产的必要性） 再生产资源是否会超过仓库的容量
    //      2 消费者：每次被唤醒后，都判断（是否可以消费）消费之后是否会出现 资源负数，即，我要的资源，是否都充足
    //
    // 2 方案二：（既然是Object#notifyAll 引起的问题，就不让这种情况发生）
    //      1 设置两把锁，消费者锁和生产者锁
    //      2 所有生产者共享生产者锁，所有消费者共享消费者锁

    public static void main(String[] args) throws Exception {

        Data data = new Data();

        // A 线程，生产资源 10 个
        new Thread(()->{
            for (int i = 0; i < 555; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        // B 线程，消费资源 10 个
        new Thread(()->{
            for (int i = 0; i < 555; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();


        // C 线程，生产资源 10 个
        new Thread(()->{
            for (int i = 0; i < 666; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        // D 线程，消费资源 10 个
        new Thread(()->{
            for (int i = 0; i < 666; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }

    static class Data{
        // 当前资源个数
        private int data = 0;
        // 资源仓库的最大容量为 3
        private final int MAX_SIZE = 3;

        // +1
        public synchronized void increment() throws InterruptedException {
            // 资源充裕，等待消费者消费
            // if (data >= 1){
            //     // 无限阻塞，直到被唤醒
            //     this.wait();
            // }
            while (data + 1 > MAX_SIZE){
                // 没必要生产，无限阻塞，直到被唤醒
                this.wait();
            }

            // 生产
            data++;
            System.out.println("[" + Thread.currentThread().getName() + "], data=" + data);
            // 随机唤醒一个线程
            // 这里其实应该唤醒一个消费者
            // 但是，由于唤醒是随机的，所以，可能唤醒生产者
            // 所以，在唤醒之后，生产者要判断是否有必要生产
            // if 应该换成 while
            this.notifyAll();
        }

        // -1
        public synchronized void decrement() throws InterruptedException {
            // 资源不足，等待生产者生产
            // if (data <= 0){
            //     this.wait();
            // }
            while (data - 1 < 0){
                // 资源不足，无限等待，直到被唤醒
                this.wait();
            }

            // 消费
            data--;
            System.out.println("[" + Thread.currentThread().getName() + "], data=" + data);
            // 随机唤醒一个线程
            // 这里其实应该唤醒一个生产者
            // 但是，由于唤醒是随机的，所以，可能唤醒消费者
            // 所以，在唤醒之后，消费者要判断是否可以消费
            // if 应该换成 while
            this.notifyAll();
        }
    }
}
