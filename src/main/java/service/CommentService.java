package service;

import dao.CommentDao;
import domain.Comment;

import java.sql.SQLException;
import java.util.Collection;

public class CommentService {
    private static CommentDao commentDao= CommentDao.getInstance();
    private static CommentService commentService=new CommentService();
    private CommentService(){}

    public static CommentService getInstance(){
        return commentService;
    }

    public Collection<Comment> findAll() throws SQLException {
        return commentDao.findAll();
    }

    public boolean add(Comment comment) throws SQLException {
        return commentDao.add(comment);
    }
}
