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
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
        while (resultSet.next()){
            User user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
            users.add(user);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return users;
    }

    public User find(Integer id) throws SQLException{
        User user = null;
        Connection connection = JdbcHelper.getConn();
        String user_sql = "SELECT * FROM user WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    public User findToUsername(String username) throws SQLException{
        User user = null;
        Connection connection = JdbcHelper.getConn();
        String user_sql = "SELECT * FROM user WHERE username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(user_sql);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            user = new User(resultSet.getInt("id"),resultSet.getString("username"),resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    public User login(String username,String password) throws SQLException {
        Connection connection=JdbcHelper.getConn();
        String loginsql="select * from user where username=? and password=?";
        PreparedStatement preparedStatement=connection.prepareStatement(loginsql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet=preparedStatement.executeQuery();
        User user=null;
        if(resultSet.next()){
            user=new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        JdbcHelper.close(preparedStatement,connection);
        return user;
    }

    public boolean update(User user) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addUser_sql = "update user set username=?,name=?,password=?,call_phone=?,address=? where id=?";
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getName());
        pstmt.setString(3,user.getPassword());
        pstmt.setString(4,user.getCallphone());
        pstmt.setString(5,user.getAddress());
        pstmt.setInt(6,user.getId());
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean add(User user) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addUser_sql = "INSERT INTO user(username,name,password,call_phone,address,balance) VALUES" + " (?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(addUser_sql);
        pstmt.setString(1,user.getUsername());
        pstmt.setString(2,user.getName());
        pstmt.setString(3,user.getPassword());
        pstmt.setString(4,user.getCallphone());
        pstmt.setString(5,user.getAddress());
        pstmt.setInt(6,1000);
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }
}
