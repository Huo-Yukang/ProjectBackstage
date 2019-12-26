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
        //结果集表游标下移一行，当有结果的时候
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
            //获取数据库连接对象
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
                        resultSet2.getString("password"),
                        resultSet2.getString("call_phone"),
                        resultSet2.getString("address"),
                        resultSet2.getInt("balance"));
            }
            preparedStatement = connection.prepareStatement("update user set balance = ? where id = ?");
            preparedStatement.setInt(1,user.getBalance() - total);
            preparedStatement.setInt(2,id);
            int affectedRowNum = preparedStatement.executeUpdate();
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
        //获得连接对象
        try {
            connection = JdbcHelper.getConn();
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            Food food = FoodService.getInstance().find(shopping.getFood().getId());
            if (food.getTotal() > 0) {
                pstmt = connection.prepareStatement
                        ("INSERT INTO shopping(user_id,food_id) VALUES" + " (?,?)");
                //为预编译的语句参数赋值
                pstmt.setInt(1, shopping.getUser().getId());
                pstmt.setInt(2, shopping.getFood().getId());
                pstmt.execute();

                pstmt = connection.prepareStatement("update food set total = ? where id = ?");
                pstmt.setInt(1, food.getTotal() - 1);
                pstmt.setInt(2, shopping.getFood().getId());
                //执行预编译对象的executeUpdate()方法，获取增加记录的行数
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

//
//    public Shopping find(Integer id) throws SQLException {
//        Shopping shopping = null;
//        //获得连接对象
//        Connection connection = JdbcHelper.getConn();
//        String findFood_sql = "SELECT * FROM shopping WHERE id=?";
//        //在该连接上创建预编译语句对象
//        PreparedStatement preparedStatement = connection.prepareStatement(findFood_sql);
//        //为预编译参数赋值
//        preparedStatement.setInt(1,id);
//        //由于id不能取重复值，故结果集中最多有一条记录
//        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Degree对象
//        //若结果集中没有记录，则本方法返回null
//        ResultSet resultSet = preparedStatement.executeQuery();
//        //若结果集仍然有下一条记录，则执行循环体
//        while (resultSet.next()){
//            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
//            Food food = FoodDao.getInstance().find(resultSet.getInt("food_id"));
//            //创建Degree对象，根据遍历结果中的id,description,no,remarks值
//            shopping = new Shopping(user,food);
//        }
//        //关闭资源
//        JdbcHelper.close(resultSet,preparedStatement,connection);
//        return shopping;
//    }

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
