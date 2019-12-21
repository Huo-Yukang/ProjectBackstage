package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Administrator;
import domain.Food;
import helper.JSONUtil;
import service.AdministratorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdministratorController")
public class AdministratorController extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String administrator_json = JSONUtil.getJSON(request);
        Food foodToUpdate = JSON.parseObject(administrator_json,Food.class);
        JSONObject message = new JSONObject();
        try {
            boolean updated = AdministratorService.getInstance().update(foodToUpdate);
            if (updated){
                message.put("message","修改成功");
            }else {
                message.put("message","未能修改");
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }
}
