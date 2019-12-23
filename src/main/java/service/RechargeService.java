package service;

import dao.RechargeDao;
import domain.Recharge;

import java.sql.SQLException;

public class RechargeService {
    private static RechargeDao rechargeDao = RechargeDao.getInstance();
    private static RechargeService rechargeService = new RechargeService();
    private RechargeService(){}
    public static RechargeService getInstance(){
        return rechargeService;
    }

    public boolean recharge(int id) throws SQLException {
        return RechargeDao.getInstance().recharge(id);
    }
}
