package service;

import dao.ShoppingDao;
import domain.Shopping;

import java.sql.SQLException;
import java.util.Collection;

public class ShoppingService {
    private static ShoppingDao shoppingDao= ShoppingDao.getInstance();
    private static ShoppingService shoppingService=new ShoppingService();
    private ShoppingService(){}

    public static ShoppingService getInstance(){
        return shoppingService;
    }

    public Collection<Shopping> findByUser_id(Integer user_id) throws SQLException {
        return shoppingDao.findByUser_id(user_id);
    }

    public Shopping find(Integer id) throws SQLException {
        return shoppingDao.find(id);
    }

    public boolean add(Shopping shopping) throws SQLException {
        return shoppingDao.add(shopping);
    }

    public boolean delete(Integer id) throws SQLException{
        return shoppingDao.delete(id);
    }

    public boolean transaction(Integer id) throws SQLException{
        return shoppingDao.transaction(id);
    }
}
