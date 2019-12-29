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
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Comment");
        while (resultSet.next()){
            Comment comment = new Comment(resultSet.getInt("id"),resultSet.getString("description"));
            comments.add(comment);
        }
        JdbcHelper.close(resultSet,statement,connection);
        return comments;
    }

    public Boolean add(Comment comment)throws SQLException{
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into comment(description) values (?)");
        preparedStatement.setString(1,comment.getDescription());
        int affectedRowNum = preparedStatement.executeUpdate();
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }

}
