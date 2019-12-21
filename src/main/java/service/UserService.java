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

    public User findToUsername(String username) throws SQLException {
        return userDao.findToUsername(username);
    }

    public boolean update(User user) throws SQLException {
        return userDao.update(user);
    }

    public boolean add(User user) throws SQLException {
        return userDao.add(user);
    }
}
