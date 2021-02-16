package test.笔试;

/**
 * Description:
 *
 * @author zwl
 * @version 1.0
 * @date 2021/2/14 22:17
 */
public class Test2 {

    // public boolean verifyPassword(String username,String password) {
    //     String sql = "select * from users as u where u.user_name=?";
    //     Connection con = getConnection () ;// getConnection（） 方法是个已有的方法可以获取到数据库连接
    //     try {
    //         PreparedStatement statement = con.prepareStatement(sql);
    //         statement.setString(1,username);
    //         ResultSet resultSet = statement.executeQuery();
    //
    //         if (resultSet.next()){
    //             String pwd = resultSet.getObject("password").toString();
    //             if (pwd.equals(password)){
    //                 return true;
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         // 打印异常日志
    //     } finally {
    //         if (con != null){
    //             con.close();
    //         }
    //     }
    //     return false;
    // }
}
