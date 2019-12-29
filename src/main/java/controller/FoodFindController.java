package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Food;
import helper.JSONUtil;
import service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/foodFind.ctl")
public class FoodFindController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_str = request.getParameter("id");
        String name_str = request.getParameter("foodname");

        JSONObject message = new JSONObject();
        try {
            if (id_str == null && name_str == null) {
                Collection<Food> foods = FoodService.getInstance().findAll();
                String food_json = JSON.toJSONString(foods, SerializerFeature.DisableCircularReferenceDetect);
                response.getWriter().println(food_json);
            } else if(id_str != null && name_str == null){
                int id = Integer.parseInt(id_str);
                Food food= FoodService.getInstance().find(id);
                String food_json = JSON.toJSONString(food);
                response.getWriter().println(food_json);
            }else{
                Food food = FoodService.getInstance().findToName(name_str);
                String food_json = JSON.toJSONString(food);
                response.getWriter().println(food_json);
            }
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
            response.getWriter().println(message);
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
            response.getWriter().println(message);
        }
    }
}
