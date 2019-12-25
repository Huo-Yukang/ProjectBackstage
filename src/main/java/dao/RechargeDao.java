package dao;

import domain.Recharge;
import domain.User;
import helper.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        System.out.println(user.getBalance());
        preparedStatement = connection.prepareStatement("update user set balance=? where id=?");
        preparedStatement.setInt(1,user.getBalance() + 500);
        preparedStatement.setInt(2,user.getId());
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement("select * from user where id =?");
        preparedStatement.setInt(1,user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        User user1 = null;
        if (resultSet.next()){
            user1 = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        return user1;
    }
}
