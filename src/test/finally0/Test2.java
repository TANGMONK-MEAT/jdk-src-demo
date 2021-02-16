package test.finally0;

/**
 * Description:
 * 测试Java中finally与return的执行顺序： try 和 catch 中都带有 return
 * @author zwl
 * @version 1.0
 * @date 2021/2/16 15:07
 */
public class Test2 {

    // 结果：当 try 代码块或者 catch 代码块中有 return 时，finally 中的代码总会被执行，且 finally 语句 return 返回之前执行。

    public static int show() {
        try {
            int a = 8 / 0;
            return 1;
        } catch (Exception e) {
            return 2;
        } finally {
            System.out.println("执行finally模块");
        }
    }
    public static void main(String[] args) {
        System.out.println(show());

        // 控制台打印：
        // 执行finally模块
        // 2
    }

}
