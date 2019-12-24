package dao;

import domain.Administrator;
import domain.Business;
import domain.Food;
import domain.User;
import helper.JdbcHelper;
import service.FoodService;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AdministratorDao {
    private static AdministratorDao administratorDao=
            new AdministratorDao();
    private AdministratorDao(){}
    public static AdministratorDao getInstance(){
        return administratorDao;
    }

    public Set<Administrator> findAll() throws SQLException {
        Set<Administrator> administrators = new HashSet<Administrator>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrator");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Administrator对象
            Administrator administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"));
            //向businesss集合中添加Administrator对象
            administrators.add(administrator);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return administrators;
    }
    
    public Administrator find(Integer id) throws SQLException{
        Administrator administrator = null;
        //获得连接对象
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String administrator_sql = "SELECT * FROM administrator WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(administrator_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
        //若结果集中没有记录，则本方法返回null
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Administrator对象
            administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return administrator;
    }

    public Boolean update(Food food) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("update food set total = ?,price = ? where id = ?");
        preparedStatement.setInt(1,food.getTotal());
        preparedStatement.setInt(2,food.getPrice());
        preparedStatement.setInt(3,food.getId());
        int affectedRowNum = preparedStatement.executeUpdate();
        return affectedRowNum>0;
    }

    public Boolean delete(Integer id)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from food where id = ?");
        preparedStatement.setInt(1,id);
        int affectedRowNum = preparedStatement.executeUpdate();
        return affectedRowNum>0;
    }

    public static Administrator login(String username, String password) throws SQLException {
        //获得连接对象
        Connection connection=JdbcHelper.getConn();
        //创建sql语句
        String loginsql="select * from administrator where admername=? and password=?";
        //在该连接上创建预编译语句
        PreparedStatement preparedStatement=connection.prepareStatement(loginsql);
        //为预编译语句赋值
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        //获得结果集对象
        ResultSet resultSet=preparedStatement.executeQuery();
        Administrator administrator=null;
        if(resultSet.next()){
            administrator=new Administrator(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
        }
        //关闭
        JdbcHelper.close(preparedStatement,connection);
        return administrator;
    }
}
