package dao;

import domain.Business;
import helper.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BusinessDao {
    private static BusinessDao businessDao=
            new BusinessDao();
    private BusinessDao(){}
    public static BusinessDao getInstance(){
        return businessDao;
    }
    public Business find() throws SQLException {
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Business");
        Business business = null;
        if (resultSet.next()){
             business = new Business(resultSet.getInt("id"),resultSet.getString("shopname"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
        }
        JdbcHelper.close(resultSet,statement,connection);
        return business;
    }

    public boolean addmoney(Business business,Integer money) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addMoney_sql = "update business set balance = ?";
        PreparedStatement pstmt = connection.prepareStatement(addMoney_sql);
        pstmt.setInt(1,money + business.getBalance());
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }
}
