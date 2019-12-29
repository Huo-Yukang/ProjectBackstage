package dao;

import domain.User;
import helper.JdbcHelper;

import java.sql.*;

public class RechargeDao {
    private static RechargeDao rechargeDao =
            new RechargeDao();

    private RechargeDao() {
    }

    public static RechargeDao getInstance() {
        return rechargeDao;
    }

    public User recharge(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = JdbcHelper.getConn();
        preparedStatement = connection.prepareStatement("select  * from user where id=?");
        preparedStatement.setInt(1,user.getId());
        ResultSet resultSet0 = preparedStatement.executeQuery();
        User user0 = null;
        if (resultSet0.next()){
            user0 = new User(resultSet0.getInt("id"),
                    resultSet0.getString("username"),
                    resultSet0.getString("name"),
                    resultSet0.getString("password"),
                    resultSet0.getString("call_phone"),
                    resultSet0.getString("address"),
                    resultSet0.getInt("balance"));
        }
        preparedStatement = connection.prepareStatement("update user set balance=? where id=?");
        preparedStatement.setInt(1,user0.getBalance() + 500);
        preparedStatement.setInt(2,user0.getId());
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("select * from user where id =?");
        preparedStatement.setInt(1,user0.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        User user1 = null;
        if (resultSet.next()){
            user1 = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        return user1;
    }

    public User getBalance(Integer id)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user1 = null;
        if (resultSet.next()){
            user1 = new User(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        return user1;
    }
}
