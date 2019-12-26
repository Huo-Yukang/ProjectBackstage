package service;

import dao.FoodDao;
import domain.Food;

import java.sql.SQLException;
import java.util.Collection;

public class FoodService {
    private static FoodDao foodDao= FoodDao.getInstance();
    private static FoodService foodService=new FoodService();
    private FoodService(){}

    public static FoodService getInstance(){
        return foodService;
    }

    public Collection<Food> findAll() throws SQLException {
        return foodDao.findAll();
    }

    public Food find(Integer id) throws SQLException {
        return foodDao.find(id);
    }

    public boolean update(Food food) throws SQLException {
        return foodDao.update(food);
    }

    public Food findToName(String name) throws  SQLException{
        return foodDao.findToName(name);
    }

    public boolean add(Food food) throws SQLException {
        return foodDao.add(food);
    }

    public boolean delete(Integer id) throws SQLException{
        return foodDao.delete(id);
    }
}
