/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/16 11:05
 */
public class Test {

    private static Integer y = 10;



    public static void updateX(int value) {

        value = 3 * value;

    }

    public static void main(String[] args) {

        updateX(y);
        System.out.println(y);

    }



}