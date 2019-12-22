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
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Business");
        //若结果集仍然有下一条记录，则执行循环体
        Business business = null;
        if (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Business对象
             business = new Business(resultSet.getInt("id"),resultSet.getString("shopname"),resultSet.getString("call_phone"),resultSet.getString("address"),resultSet.getInt("balance"));
            //向businesss集合中添加Business对象
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return business;
    }

    public boolean addmoney(Business business,Integer money) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addMoney_sql = "update business set balance = ?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addMoney_sql);
        pstmt.setInt(1,money + business.getBalance());
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }
}
