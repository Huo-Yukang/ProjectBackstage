package controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Administrator;
import domain.User;
import helper.JSONUtil;
import service.AdministratorService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdministratorLogin.ctl")
public class AdministratorLoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");
        String login_json = JSONUtil.getJSON(request);
        Administrator administrator = JSON.parseObject(login_json, Administrator.class);
        JSONObject message = new JSONObject();
        try{
            Administrator loggedAdministrator = AdministratorService.getInstance().login(administrator.getAdmername(),administrator.getPassword());
            if (loggedAdministrator != null){
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(2 * 60);
                session.setAttribute("currentUser",loggedAdministrator);
                String loggedUser_json=JSON.toJSONString(loggedAdministrator);
                response.getWriter().println(loggedUser_json);
                return;
            }else{
                message.put("message","用户名或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message.put("message","数据库操作异常");
        }catch (Exception e){
            e.printStackTrace();
            message.put("message","网络异常");
        }
        response.getWriter().println(message);
    }
}