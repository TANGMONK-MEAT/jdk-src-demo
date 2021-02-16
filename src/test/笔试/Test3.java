package test.笔试;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/15 17:39
 */
public class Test3 {

    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            try {
                for (int i = 0; i <= 5; i++) {
                    data.printOs();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },"A").start();
        new Thread(()->{
            try {
                for (int i = 0; i < 5; i++) {
                    data.printJs();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },"B").start();

    }

    static class Data{
        private int number = 0;

        public synchronized void printOs() throws InterruptedException {
            while (number % 2 != 0){
                this.wait();
            }
            System.out.println("[" + Thread.currentThread().getName() + "] " + number);
            number++;
            this.notifyAll();
        }

        public synchronized void printJs() throws InterruptedException {
            while (number % 2 == 0){
                this.wait();
            }
            System.out.println("[" + Thread.currentThread().getName() + "] " + number);
            number++;
            this.notifyAll();
        }
    }
}
