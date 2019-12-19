package service;

import dao.UserDao;
import domain.User;

import java.sql.SQLException;
import java.util.Collection;

public class UserService {
    private static UserDao userDao= UserDao.getInstance();
    private static UserService userService=new UserService();
    private UserService(){}

    public static UserService getInstance(){
        return userService;
    }

    public Collection<User> findAll() throws SQLException {
        return userDao.findAll();
    }

    public User find(Integer id) throws SQLException {
        return userDao.find(id);
    }

    public boolean update(User user) throws SQLException {
        return userDao.update(user);
    }

    public boolean add(User user) throws SQLException {
        return userDao.add(user);
    }

    public boolean  delete(User user) throws SQLException{
        return userDao.delete(user);
    }
}
