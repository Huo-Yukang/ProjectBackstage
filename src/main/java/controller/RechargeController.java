package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.User;
import helper.JSONUtil;
import service.RechargeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/recharge.ctl")
public class RechargeController  extends HttpServlet {
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JSONUtil.getJSON(request);
        User userToUpdate = JSON.parseObject(user_json,User.class);
        JSONObject message = new JSONObject();
        try {
            User recharge = RechargeService.getInstance().recharge(userToUpdate);
            String userToJson = JSON.toJSONString(recharge, SerializerFeature.DisableCircularReferenceDetect);
            response.getWriter().println(userToJson);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = request.getParameter("id");
        int id = Integer.parseInt(user_id);
        try {
            User userTojson = RechargeService.getInstance().getBalance(id);
            String user_json = JSON.toJSONString(userTojson, SerializerFeature.DisableCircularReferenceDetect);
            response.getWriter().println(user_json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}