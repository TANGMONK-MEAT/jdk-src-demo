package test.threadBasic;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/12 18:32
 */
public class WaitAndNotifyTest {

    public static void main(String[] args) {
        Message msg = new Message("process it");
        Waiter waiter = new Waiter(msg);

        for (int i = 0; i < 10; i++) {
            new Thread(waiter, "waiter" + i).start();
        }

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        System.out.println("All the threads are started");

    }

    static class Message {
        private String msg;

        public Message(String str) {
            this.msg = str;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String str) {
            this.msg = str;
        }

    }

    static class Waiter implements Runnable {
        private final Message msg;

        public Waiter(Message m) {
            this.msg = m;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            synchronized (msg) {
                try {
                    System.out.println(name + " waiting to get notified at time:" + System.nanoTime());
                    msg.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " waiter thread got notified at time:" + System.nanoTime());
                System.out.println(name + " processed: " + msg.getMsg());
            }
        }
    }

    static class Notifier implements Runnable {
        private final Message msg;

        public Notifier(Message msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " started");
            try {
                TimeUnit.SECONDS.sleep(1);
                synchronized (msg) {
                    msg.setMsg(name + " Notifier work done");
                    // msg.notify();
                    msg.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
