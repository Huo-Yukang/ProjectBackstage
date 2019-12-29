package dao;
import domain.Food;
import helper.JdbcHelper;
import service.FoodService;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FoodDao {

    private static FoodDao foodDao=
            new FoodDao();
    private FoodDao(){}
    public static FoodDao getInstance(){
        return foodDao;
    }
    public Set<Food> findAll() throws SQLException {
        Set<Food> foods = new HashSet<Food>();
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Food");
        while (resultSet.next()){
            Food food = new Food(resultSet.getInt("id"),resultSet.getString("foodno"),resultSet.getString("foodname"),resultSet.getInt("price"),resultSet.getInt("total"));
            foods.add(food);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return foods;
    }

    public Food find(Integer id) throws SQLException{
        Food food = null;
        Connection connection = JdbcHelper.getConn();
        String food_sql = "SELECT * FROM food WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(food_sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            food = new Food(resultSet.getInt("id"),resultSet.getString("foodno"),resultSet.getString("foodname"),resultSet.getInt("price"),resultSet.getInt("total"));
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return food;
    }

    public Food findToName(String name) throws SQLException{
        Food food = null;
        Connection connection = JdbcHelper.getConn();
        String food_sql = "SELECT * FROM food WHERE foodname=?";
        PreparedStatement preparedStatement = connection.prepareStatement(food_sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            food = new Food(resultSet.getInt("id"),resultSet.getString("foodno"),resultSet.getString("foodname"),resultSet.getInt("price"),resultSet.getInt("total"));
        }
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return food;
    }

    public boolean update(Food food) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        String addFood_sql = "update food set foodno=?,foodname=?,price=?,total=? where id=?";
        PreparedStatement pstmt = connection.prepareStatement(addFood_sql);
        pstmt.setString(1,food.getFoodno());
        pstmt.setString(2,food.getFoodname());
        pstmt.setInt(3,food.getPrice());
        pstmt.setInt(4,food.getTotal());
        pstmt.setInt(5,food.getId());
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public Boolean add(Food food)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into food(foodno, foodname, price, total) values (?,?,?,?)");
        preparedStatement.setString(1,food.getFoodno());
        preparedStatement.setString(2,food.getFoodname());
        preparedStatement.setInt(3,food.getPrice());
        preparedStatement.setInt(4,food.getTotal());
        int affectedRowNum = preparedStatement.executeUpdate();
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

    public boolean  delete(int id) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        String delete = "DELETE FROM food WHERE ID =?";
        PreparedStatement pstmt = connection.prepareStatement(delete);
        pstmt.setInt(1,id);
        System.out.println(id);
        int delete1 = pstmt.executeUpdate();
        return delete1>0;
    }
}
