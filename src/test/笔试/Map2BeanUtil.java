package test.笔试;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/14 21:57
 */
public class Map2BeanUtil {

    /**
     * Map转换成JavaBean
     *
     * @param map Map集合
     * @param beanClass bean的Class对象
     * @param <T> bean的类型
     * @return 包含bean属性字段的Map
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws IllegalAccessException, InstantiationException {
        if (map == null || map.size() == 0){
            return beanClass.newInstance();
        }
        T instance = beanClass.newInstance();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field f : fields){
            int mod = f.getModifiers();
            // final 修饰的字段无法赋值
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            // 访问私有属性
            f.setAccessible(true);
            // 设置 value
            f.set(instance,map.get(f.getName()));
        }
        return instance;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", "影像管理岗");
        map.put("roleCode", "Role-2001");
        map.put("level", 1);
        map.put("createTime", new Date());
        map.put("vaild", false);

        try {
            RoleInfo role = Map2BeanUtil.mapToObject(map, RoleInfo.class);
            System.out.println(role.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class RoleInfo {

        private static String roleName;
        private  String roleCode;
        private  final Integer level = 0;
        private  Date createTime;
        private  Boolean vaild;


        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            RoleInfo.roleName = roleName;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public Integer getLevel() {
            return level;
        }

        // public void setLevel(Integer level) {
        //     this.level = level;
        // }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Boolean getVaild() {
            return vaild;
        }

        public void setVaild(Boolean vaild) {
            this.vaild = vaild;
        }

        @Override
        public String toString(){
            return "RoleInfo[roleName="+roleName +",roleCode="+roleCode+",level="+level+",createTime="+createTime+",vaild="+vaild+"]";
        }
    }
}
