package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
            System.out.println(userToUpdate);
            User recharge = RechargeService.getInstance().recharge(userToUpdate);
            System.out.println(userToUpdate);
            response.getWriter().println(recharge);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
