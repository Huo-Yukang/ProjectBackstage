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

@WebServlet("/foodUpdate.ctl")
public class FoodUpdateController extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String food_json = JSONUtil.getJSON(request);
        Food foodToAdd = JSON.parseObject(food_json,Food.class);
        JSONObject message = new JSONObject();
        try {
            FoodService.getInstance().update(foodToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
        }
        response.getWriter().println(message);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        Food foodToAdd = JSON.parseObject(comment_json,Food.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            FoodService.getInstance().add(foodToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        int id = Integer.parseInt(id_str);
        message.put("message", "删除成功");
        try {
            FoodService.getInstance().delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }
        response.getWriter().println(message);
    }
}
