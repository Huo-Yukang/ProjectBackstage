package dao;

import domain.Business;
import domain.Food;
import domain.Shopping;
import domain.User;
import helper.JdbcHelper;
import service.BusinessService;
import service.FoodService;
import service.ShoppingService;
import service.UserService;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShoppingDao {
    private static ShoppingDao shoppingDao=
            new ShoppingDao();
    private ShoppingDao(){}
    public static ShoppingDao getInstance(){
        return shoppingDao;
    }

    public Shopping find(Integer shopping_id)throws SQLException{
        Shopping shopping = null;
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from shopping where id = ?");
        preparedStatement.setInt(1,shopping_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            User user = UserService.getInstance().find(resultSet.getInt("user_id"));
            Food food = FoodService.getInstance().find(resultSet.getInt("food_id"));
            shopping = new Shopping(user,food);
        }
        JdbcHelper.close(preparedStatement,connection);
        return shopping;
    }

    public Collection<Shopping> findByUser_id(Integer user_id) throws SQLException {
        Set<Shopping> shoppings = new HashSet<Shopping>();
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from shopping where user_id = ?");
        preparedStatement.setInt(1,user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
            Food food = FoodDao.getInstance().find(resultSet.getInt("food_id"));
            Shopping shopping = new Shopping(resultSet.getInt("id"),user,food);
            shoppings.add(shopping);
        }
        return shoppings;
    }

    public boolean transaction(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean affected = null;
        try {
            connection = JdbcHelper.getConn();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("select * from business");
            ResultSet resultSet = preparedStatement.executeQuery();
            Business business = null;
            if (resultSet.next()){
                business = new Business(
                        resultSet.getString("shopname"),
                        resultSet.getString("call_phone"),
                        resultSet.getString("address"),
                        resultSet.getInt("balance"));
            }
            preparedStatement = connection.prepareStatement("select food_id from shopping where user_id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            int total = 0;
            while (resultSet1.next()){
                int food_id = resultSet1.getInt("food_id");
                preparedStatement = connection.prepareStatement("select * from food where id = ?");
                preparedStatement.setInt(1,food_id);
                ResultSet resultSet0 = preparedStatement.executeQuery();
                if(resultSet0.next()) {
                    Food food = new Food(resultSet0.getString("foodno"),
                            resultSet0.getString("foodname"),
                            resultSet0.getInt("price"),
                            resultSet0.getInt("total"));
                    total = total + food.getPrice();
                }
            }

            preparedStatement = connection.prepareStatement("select * from user where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet2 = preparedStatement.executeQuery();
            User user = null;
            if (resultSet2.next()){
                user = new User(resultSet2.getString("username"),
                        resultSet2.getString("name"),
                        resultSet2.getString("password"),
                        resultSet2.getString("call_phone"),
                        resultSet2.getString("address"),
                        resultSet2.getInt("balance"));
            }
            preparedStatement = connection.prepareStatement("update user set balance = ? where id = ?");
            preparedStatement.setInt(1,user.getBalance() - total);
            preparedStatement.setInt(2,id);
            int affectedRowNum = preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("delete from shopping where user_id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            if(user.getBalance() < total){
                connection.rollback();
                affectedRowNum = 0;
            }else {
                BusinessService.getInstance().addmoney(business,total);
            }
            connection.commit();
            affected = affectedRowNum > 0;
        }catch (SQLException e){
            if(connection != null){
                e.printStackTrace();
                connection.rollback();
            }
        }finally {
            if(connection != null){
                connection.setAutoCommit(true);
            }
            JdbcHelper.close(preparedStatement,connection);
        }
        return affected;
    }

    public boolean add(Shopping shopping) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Boolean affected = false;
        try {
            connection = JdbcHelper.getConn();
            Food food = FoodService.getInstance().find(shopping.getFood().getId());
            if (food.getTotal() > 0) {
                pstmt = connection.prepareStatement
                        ("INSERT INTO shopping(user_id,food_id) VALUES" + " (?,?)");
                pstmt.setInt(1, shopping.getUser().getId());
                pstmt.setInt(2, shopping.getFood().getId());
                pstmt.execute();
                pstmt = connection.prepareStatement("update food set total = ? where id = ?");
                pstmt.setInt(1, food.getTotal() - 1);
                pstmt.setInt(2, shopping.getFood().getId());
                int affectedRowNum = pstmt.executeUpdate();
                System.out.println("增加了 " + affectedRowNum + " 条");
                affected = true;
            }
        }catch (SQLException e){
            if(connection != null){
                e.printStackTrace();
                connection.rollback();
            }
        }finally {
            if(connection != null){
                connection.setAutoCommit(true);
            }
            JdbcHelper.close(pstmt,connection);
        }
        return affected;
    }


    public boolean delete(Integer shopping_id) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        Shopping shopping = ShoppingService.getInstance().find(shopping_id);
        PreparedStatement pstmt = connection.prepareStatement("update food set total = ? where id = ?");
        pstmt.setInt(1,shopping.getFood().getTotal() + 1);
        pstmt.setInt(2,shopping.getFood().getId());
        pstmt.execute();
        pstmt = connection.prepareStatement("DELETE FROM shopping WHERE ID =?");
        pstmt.setInt(1,shopping_id);
        int delete = pstmt.executeUpdate();
        return delete>0;
    }
}
