package service;

import dao.AdministratorDao;
import domain.Administrator;
import domain.Food;

import java.sql.SQLException;
import java.util.Collection;

public class AdministratorService {
    private static AdministratorDao administratorDao= AdministratorDao.getInstance();
    private static AdministratorService administratorService=new AdministratorService();
    private AdministratorService(){}

    public static AdministratorService getInstance(){
        return administratorService;
    }

    public Collection<Administrator> findAll() throws SQLException {
        return administratorDao.findAll();
    }

    public Administrator find(Integer id) throws SQLException {
        return administratorDao.find(id);
    }

    public boolean update(Food food) throws SQLException {
        return administratorDao.update(food);
    }

    public boolean  delete(int id) throws SQLException{
        return administratorDao.delete(id);
    }
}
