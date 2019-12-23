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

    public boolean recharge(int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = JdbcHelper.getConn();
        preparedStatement = connection.prepareStatement("select * from user where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User(resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("call_phone"),
                    resultSet.getString("address"),
                    resultSet.getInt("balance"));
        }
        System.out.println(user.getBalance());
        preparedStatement = connection.prepareStatement("update user set balance=? where id=?");
        preparedStatement.setInt(1,user.getBalance() + 500);
        preparedStatement.setInt(2,id);
        int affectedRowNum = preparedStatement.executeUpdate();
        System.out.println("修改了" + affectedRowNum + "条语句");
        return affectedRowNum>0;
    }
}
