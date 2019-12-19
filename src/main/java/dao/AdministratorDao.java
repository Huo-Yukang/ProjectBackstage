package dao;

import domain.Administrator;
import domain.Business;
import domain.Food;
import domain.User;
import helper.JdbcHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Set<Administrator> businesss = new HashSet<Administrator>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM administrator");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
            Business business = BusinessDao.getInstance().find(resultSet.getInt("business_id"));
            Food food = FoodDao.getInstance().find(resultSet.getInt("food_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Administrator对象
            Administrator administrator = new Administrator(resultSet.getInt("id"),resultSet.getString("admername"),resultSet.getString("password"),user,business,food);
            //向businesss集合中添加Administrator对象
            businesss.add(business);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return businesss;
    }
}
