package test.finally0;

/**
 * Description:
 * 测试Java中finally与return的执行顺序：try 和 catch 中带有 return
 * @author zwl
 * @version 1.0
 * @date 2021/2/16 15:04
 */
public class Test1 {

    // 结果：先执行 finally 语句块中的代码，再 return

    public static int show() {
        try {
            return 1;
        } finally {
            System.out.println("执行finally模块");
        }
    }

    public static void main(String[] args) {
        System.out.println(show());

        // 控制台打印：
        // 执行finally模块
        // 1
    }
}
