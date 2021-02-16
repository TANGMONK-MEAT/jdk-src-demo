package test.finally0;

/**
 * Description:
 * try 和 catch 和 finally 中都带有 return
 * @author zwl
 * @version 1.0
 * @date 2021/2/16 15:09
 */
public class Test3 {

    // 结果：当 finally 有返回值时，会直接返回该值，不会去返回 try 代码块或者 catch 代码块中的返回值。
    // 注：finally 代码块中最好不要包含 return 语句，否则程序会提前退出。

    public static int show() {
        try {
            int a = 8 / 0;
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            System.out.println("执行finally模块");
            return 0;
        }
    }
    public static void main(String[] args) {
        System.out.println(show());

        // 控制台打印：
        // 执行finally模块
        // 0
    }

}
