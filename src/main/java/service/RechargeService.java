package service;

import dao.RechargeDao;
import domain.Recharge;
import domain.User;

import java.sql.SQLException;

public class RechargeService {
    private static RechargeDao rechargeDao = RechargeDao.getInstance();
    private static RechargeService rechargeService = new RechargeService();
    private RechargeService(){}
    public static RechargeService getInstance(){
        return rechargeService;
    }

    public User recharge(User user) throws SQLException {
        return RechargeDao.getInstance().recharge(user);
    }
    public User getBalance(Integer id)throws SQLException{
        return rechargeDao.getInstance().getBalance(id);
    }
}
