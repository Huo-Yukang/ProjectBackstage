package dao;

import domain.Food;
import domain.Shopping;
import domain.User;
import helper.JdbcHelper;

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



    public boolean add(Shopping shopping) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addFoodToShopping_sql = "INSERT INTO shopping(no,food_id) VALUES" + " (?,?)";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addFoodToShopping_sql);
        //为预编译的语句参数赋值
        pstmt.setInt(1,shopping.getUser().getId());
        pstmt.setInt(2,shopping.getFood().getId());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }


    public Shopping find(Integer id) throws SQLException {
        Shopping shopping = null;
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String findFood_sql = "SELECT * FROM shopping WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findFood_sql);
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
            //创建Degree对象，根据遍历结果中的id,description,no,remarks值
            shopping = new Shopping(user,food);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return shopping;
    }

    public boolean delete(Shopping shopping) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String delete = "DELETE FROM shopping WHERE ID =?";
        PreparedStatement pstmt = connection.prepareStatement(delete);
        pstmt.setInt(1,shopping.getId());
        int delete1 = pstmt.executeUpdate();
        return delete1>0;
    }
}
