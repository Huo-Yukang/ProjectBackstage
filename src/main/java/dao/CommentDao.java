package dao;

import domain.Comment;
import domain.User;
import helper.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CommentDao {
    private static CommentDao commentDao=
            new CommentDao();
    private CommentDao(){}
    public static CommentDao getInstance(){
        return commentDao;
    }

    public Set<Comment> findAll() throws SQLException {
        Set<Comment> comments = new HashSet<Comment>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Comment");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Comment对象
            User user = UserDao.getInstance().find(resultSet.getInt("user_id"));
            Comment comment = new Comment(resultSet.getInt("id"),resultSet.getString("description"),user);
            //向businesss集合中添加Comment对象
            comments.add(comment);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return comments;
    }

    public Boolean add(Comment comment)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into comment(description,user_id) values (?,?)");
        preparedStatement.setString(1,comment.getDescription());
        preparedStatement.setInt(2,comment.getUser().getId());
        int affectedRowNum = preparedStatement.executeUpdate();
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

}
