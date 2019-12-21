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
             business = new Business(resultSet.getInt("id"),resultSet.getString("shopname"),resultSet.getString("call_phone"),resultSet.getString("address"));
            //向businesss集合中添加Business对象
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return business;
    }
}
