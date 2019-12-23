package dao;

import domain.User;
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
            //以当前记录中的id,description,no,remarks值为参数，创建User对象
            User user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
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
        String user_sql = "SELECT * FROM user WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
        //若结果集中没有记录，则本方法返回null
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
            //向users集合中添加User对象
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    public User findToUsername(String username) throws SQLException{
        User user = null;
        //获得连接对象
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String user_sql = "SELECT * FROM user WHERE username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,username);
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
        //若结果集中没有记录，则本方法返回null
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建User对象，根据遍历结果中的id,description,no,remarks值
            user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
            //向users集合中添加User对象
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    public User login(String username,String password) throws SQLException {
        //获得连接对象
        Connection connection=JdbcHelper.getConn();
        //创建sql语句
        String loginsql="select * from user where username=? and password=?";
        //在该连接上创建预编译语句
        PreparedStatement preparedStatement=connection.prepareStatement(loginsql);
        //为预编译语句赋值
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        //获得结果集对象
        ResultSet resultSet=preparedStatement.executeQuery();
        User user=null;
        if(resultSet.next()){
            user=new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        //关闭
        JdbcHelper.close(preparedStatement,connection);
        return user;
    }

    public boolean update(User user) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addUser_sql = "update user set username=?,password=?,call_phone=?,address=? where id=?";
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
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addUser_sql = "INSERT INTO user(username,password,call_phone,address,balance) VALUES" + " (?,?,?,?,?)";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getPassword());
        pstmt.setString(3,user.getCallphone());
        pstmt.setString(4,user.getAddress());
        pstmt.setInt(5,1000);
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }
}
