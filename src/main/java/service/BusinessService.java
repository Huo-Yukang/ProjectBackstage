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

    public Business find() throws SQLException {
        return businessDao.find();
    }
    public boolean addmoney(Business business,Integer money) throws SQLException {
        return businessDao.addmoney(business,money);
    }
}
