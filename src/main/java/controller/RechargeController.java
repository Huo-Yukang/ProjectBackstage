package controller;

import com.alibaba.fastjson.JSONObject;
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
        String user_id_str = request.getParameter("id");
        int user_id = Integer.parseInt(user_id_str);
        JSONObject message = new JSONObject();
        try {
            boolean recharge = RechargeService.getInstance().recharge(user_id);
            if(recharge == true){
                message.put("message","充值成功");
            }else {
                message.put("message","充值失败");
            }
            response.getWriter().println(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
