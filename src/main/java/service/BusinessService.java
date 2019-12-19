package service;

import dao.BusinessDao;
import domain.Business;

import java.sql.SQLException;
import java.util.Collection;

public class BusinessService {
    private static BusinessDao businessDao= BusinessDao.getInstance();
    private static BusinessService businessService=new BusinessService();
    private BusinessService(){}

    public static BusinessService getInstance(){
        return businessService;
    }

    public Collection<Business> findAll() throws SQLException {
        return businessDao.findAll();
    }

    public Business find(Integer id) throws SQLException {
        return businessDao.find(id);
    }

    public boolean update(Business business) throws SQLException {
        return businessDao.update(business);
    }

    public boolean add(Business business) throws SQLException {
        return businessDao.add(business);
    }

    public boolean  delete(Business business) throws SQLException{
        return businessDao.delete(business);
    }
}
