package test.designMode;

/**
 * Description:
 * 双重校验锁 单例设计模式
 * @author zwl
 * @version 1.0
 * @date 2021/2/10 14:37
 */
public class Singleton {

    // volatile关键字可以防止jvm指令重排优化
    // 因为 singleton = new Singleton() 这句话可以分为三步：
    //         1. 为 singleton 分配内存空间；
    //         2. 初始化 singleton；
    //         3. 将 singleton 指向分配的内存空间。
    // 但是由于JVM具有指令重排的特性，执行顺序有可能变成 1-3-2。
    // 指令重排在单线程下不会出现问题，但是在多线程下会导致一个线程获得一个未初始化的实例。
    // 例如：
    //      线程T1执行了1和3，此时T2调用 getInstance() 后发现 singleton 不为空，因此返回 singleton， 但是此时的 singleton 还没有被初始化。
    // 使用 volatile 会禁止JVM指令重排，从而保证在多线程下也能正常执行。
    private static volatile Singleton instance = null;

    // volatile关键字的第二个作用，保证变量在多线程运行时的可见性：
    // 在 JDK1.2 之前，Java的内存模型实现总是从主存（即共享内存）读取变量，是不需要进行特别的注意的。
    //
    // 而在当前 的 Java 内存模型下，线程可以把变量保存本地内存（比如机器的寄存器）中，而不是直接在主存中进行读写。
    // 这就 可能造成一个线程在主存中修改了一个变量的值，而另外一个线程还继续使用它在寄存器中的变量值的拷贝，造成数 据的不一致。
    // 要解决这个问题，就需要把变量声明为 volatile，这就指示 JVM，这个变量是不稳定的，每次使用它都到主存中进行 读取。

    private Singleton(){}

    public static Singleton getInstance(){

        // 提高代码执行效率，由于单例模式只要一次创建实例即可，
        // 所以当创建了一个实例之后，再次调用getInstance方法就不必要进入同步代码块，不用竞争锁
        if(instance == null){
            // 同步锁，每次只允许一个线程执行,其余线程 在同步池中等待
            synchronized (Singleton.class){
                // 防止二次创建实例
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
