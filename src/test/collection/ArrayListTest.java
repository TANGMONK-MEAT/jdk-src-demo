package test.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 调试 ArrayList 源码
 * @author zwl
 * @version 1.0
 * @date 2020/7/31 20:32
 */
public class ArrayListTest {

    public static void main(String[] args) {

        List<Object> list =  new ArrayList<Object>();
        list.add("123");
        System.out.println("【ArrayListTest】" + list);
    }
}
