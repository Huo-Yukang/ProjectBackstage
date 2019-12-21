package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Business;
import service.BusinessService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/business.ctl")
public class BusinessController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            Business business = BusinessService.getInstance().find();
            String business_json = JSON.toJSONString(business);
            //响应school_json到前端
            response.getWriter().println(business_json);
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
}
