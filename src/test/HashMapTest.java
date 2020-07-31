package test;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 调试HashMap源码
 * @author zwl
 * @version 1.0
 * @date 2020/7/31 18:59
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String,Object>(6);
        map.put("str1","str1");
        System.out.println("【test】" + map);
    }
}
