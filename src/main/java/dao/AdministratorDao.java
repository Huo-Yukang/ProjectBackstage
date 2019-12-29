package dao;



import domain.Administrator;
import domain.Food;
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
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrator");
        while (resultSet.next()){
            Administrator administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"));
            administrators.add(administrator);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return administrators;
    }
    
    public Administrator find(Integer id) throws SQLException{
        Administrator administrator = null;
        Connection connection = JdbcHelper.getConn();
        String administrator_sql = "SELECT * FROM administrator WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(administrator_sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"));
        }
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
        Connection connection=JdbcHelper.getConn();
        String loginsql="select * from administrator where admername=? and password=?";
        PreparedStatement preparedStatement=connection.prepareStatement(loginsql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet=preparedStatement.executeQuery();
        Administrator administrator=null;
        if(resultSet.next()){
            administrator=new Administrator(resultSet.getInt("id"),
                    resultSet.getString("admername"),
                    resultSet.getString("password"));
        }
        JdbcHelper.close(preparedStatement,connection);
        return administrator;
    }
}
