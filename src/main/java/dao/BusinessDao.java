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
    public Set<Business> findAll() throws SQLException {
        Set<Business> businesss = new HashSet<Business>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Business");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Business对象
            Business business = new Business(resultSet.getInt("id"),resultSet.getString("shopname"),resultSet.getString("call_phone"),resultSet.getString("address"));
            //向businesss集合中添加Business对象
            businesss.add(business);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return businesss;
    }

    public Business find(Integer id) throws SQLException{
        Set<Business> businesss = new HashSet<Business>();
        //获得连接对象
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("select * from Business");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建Business对象，根据遍历结果中的id,description,no,remarks值
            Business business = new Business(resultSet.getInt("id"),resultSet.getString("shopname"),resultSet.getString("call_phone"),resultSet.getString("address"));
            //向businesss集合中添加Business对象
            businesss.add(business);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        Business desiredBusiness = null;
        for (Business business : businesss) {
            if(id.equals(business.getId())){
                desiredBusiness =  business;
            }
        }
        return desiredBusiness;
    }

    public boolean update(Business business) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addBusiness_sql = "update business set shopname=?,call_phone=?,address=? where id=?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addBusiness_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,business.getShopname());
        pstmt.setString(2,business.getCall_phone());
        pstmt.setString(3,business.getAddress());
        pstmt.setInt(4,business.getId());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean add(Business business) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addBusiness_sql = "INSERT INTO business(shopname,call_phone,address) VALUES" + " (?,?,?,)";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addBusiness_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,business.getShopname());
        pstmt.setString(2,business.getCall_phone());
        pstmt.setString(3,business.getAddress());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean  delete(Business business) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String delete = "DELETE FROM DEGREE WHERE ID =?";
        PreparedStatement pstmt = connection.prepareStatement(delete);
        pstmt.setInt(1,business.getId());
        int delete1 = pstmt.executeUpdate();
        return delete1>0;
    }
}
