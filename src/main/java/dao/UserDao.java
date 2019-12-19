package dao;

import domain.Food;
import domain.Shopping;
import domain.User;
import helper.JdbcHelper;
import service.ShoppingService;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDao {

    private static UserDao userDao=
            new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return userDao;
    }
    public Set<User> findAll() throws SQLException {
        Set<User> users = new HashSet<User>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            Shopping shopping = ShoppingDao.getInstance().find(resultSet.getInt(""));
            //以当前记录中的id,description,no,remarks值为参数，创建User对象
            User user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("callphone"),resultSet.getString("address"),shopping);
            //向users集合中添加User对象
            users.add(user);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return users;
    }

    public User find(Integer id) throws SQLException{
        User user = null;
        //获得连接对象
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String deleteDepartment_sql = "SELECT * FROM department WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteDepartment_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
        //若结果集中没有记录，则本方法返回null
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            Shopping shopping = ShoppingDao.getInstance().find(resultSet.getInt("shopping_id"));
            user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("callphone"),resultSet.getString("address"),shopping);
            //向users集合中添加User对象
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    public boolean update(User user) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addUser_sql = "update user set username=?,password=?,callphone=?,address=? where id=?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getCallphone());
        pstmt.setString(4,user.getAddress());
        pstmt.setInt(5,user.getId());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean add(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean affected = null;
        try {
            //获取数据库连接对象
            connection = JdbcHelper.getConn();
            connection.setAutoCommit(false);
            //添加预编译语句
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO userkeshi(id,username,password,callphone,address,shopping_id) VALUES (?,?,?,?,?,?)");
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getCallphone());
            preparedStatement.setString(5,user.getAddress());
            preparedStatement.setInt(6,user.getShopping().getId());

            //执行预编译对象的executeUpdate()方法，获得删除行数
            int affectedRowNum = preparedStatement.executeUpdate();
            System.out.println("添加了" + affectedRowNum + "条记录");
            //执行预编译语句，用其返回值、影响的行为数为赋值affectedRowNum
            PreparedStatement getNewUserId = connection.prepareStatement("select id from user where no=?");
            //将teacher的no字段赋值给查询条件
            getNewUserId.setString(1,user.getUsername());
            ResultSet resultSet = getNewUserId.executeQuery();
            connection.commit();
            if (resultSet.next()) {
                Food food = FoodDao.getInstance().find(resultSet.getInt("id"));
                Shopping shoppingToadd = new Shopping(user.getUsername(),food);
                ShoppingService.getInstance().add(shoppingToadd);
            }

            affected = affectedRowNum > 0;
            return affected;
        }catch (SQLException e){
            if(connection != null){
                e.printStackTrace();
                connection.rollback();
            }
        }finally {
            if(connection != null){
                connection.setAutoCommit(true);
            }
            JdbcHelper.close(preparedStatement,connection);
        }
        return affected;
    }

    public boolean  delete(User user) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String delete = "DELETE FROM DEGREE WHERE ID =?";
        PreparedStatement pstmt = connection.prepareStatement(delete);
        pstmt.setInt(1,user.getId());
        int delete1 = pstmt.executeUpdate();
        return delete1>0;
    }
}
