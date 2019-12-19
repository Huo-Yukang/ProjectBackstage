package dao;

import domain.Food;
import helper.JdbcHelper;

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
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Food");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Food对象
            Food food = new Food(resultSet.getInt("id"),resultSet.getString("foodno"),resultSet.getString("foodname"),resultSet.getInt("price"),resultSet.getInt("total"));
            //向foods集合中添加Food对象
            foods.add(food);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return foods;
    }

    public Food find(Integer id) throws SQLException{
        Set<Food> foods = new HashSet<Food>();
        //获得连接对象
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("select * from Food");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建Food对象，根据遍历结果中的id,description,no,remarks值
            Food food = new Food(resultSet.getInt("id"),resultSet.getString("foodno"),resultSet.getString("foodname"),resultSet.getInt("price"),resultSet.getInt("total"));
            //向foods集合中添加Food对象
            foods.add(food);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        Food desiredFood = null;
        for (Food food : foods) {
            if(id.equals(food.getId())){
                desiredFood =  food;
            }
        }
        return desiredFood;
    }

    public boolean update(Food food) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addFood_sql = "update food set foodno=?,foodname=?,price=?,total=? where id=?";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addFood_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,food.getFoodno());
        pstmt.setString(2,food.getFoodname());
        pstmt.setInt(3,food.getPrice());
        pstmt.setInt(4,food.getTotal());
        pstmt.setInt(5,food.getId());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("修改了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean add(Food food) throws SQLException {
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String addFood_sql = "INSERT INTO food(foodno,foodname,price,total) VALUES" + " (?,?,?,?)";
        //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
        PreparedStatement pstmt = connection.prepareStatement(addFood_sql);
        //为预编译的语句参数赋值
        pstmt.setString(1,food.getFoodno());
        pstmt.setString(2,food.getFoodname());
        pstmt.setInt(3,food.getPrice());
        pstmt.setInt(4,food.getTotal());
        //执行预编译对象的executeUpdate()方法，获取增加记录的行数
        int affectedRowNum = pstmt.executeUpdate();
        System.out.println("增加了 "+affectedRowNum+" 条");
        return affectedRowNum > 0;
    }

    public boolean  delete(Food food) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //创建sql语句，“？”作为占位符
        String delete = "DELETE FROM DEGREE WHERE ID =?";
        PreparedStatement pstmt = connection.prepareStatement(delete);
        pstmt.setInt(1,food.getId());
        int delete1 = pstmt.executeUpdate();
        return delete1>0;
    }
}
