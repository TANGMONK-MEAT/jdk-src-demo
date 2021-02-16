package test.finally0;

/**
 * Description:
 * finally 中改变返回值
 * @author zwl
 * @version 1.0
 * @date 2021/2/16 15:12
 */
public class Test4 {

    // 结果：在 finally 代码块中改变返回值并不会改变最后返回的内容。

    public static int show() {
        int result = 0;
        try {
            result = 1;
            return result;
        } finally {
            System.out.println("执行finally模块");
            result = 2;
        }
    }
    public static void main(String[] args) {
        System.out.println(show());

        // 控制台打印：
        // 执行finally模块
        // 0
    }

}
