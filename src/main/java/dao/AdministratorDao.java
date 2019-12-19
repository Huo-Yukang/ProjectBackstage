package dao;

import domain.Administrator;
import domain.Business;
import domain.Food;
import domain.User;
import helper.JdbcHelper;

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
            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
            Food food = FoodDao.getInstance().find(resultSet.getInt("food_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Administrator对象
            Administrator administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"),user,food);
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
            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
            Food food = FoodDao.getInstance().find(resultSet.getInt("food_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Administrator对象
            administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"),user,food);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return administrator;
    }

    public Boolean update(Integer id) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("update food set total = total + 1 where id = ?");
        preparedStatement.setInt(1,id);
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
}