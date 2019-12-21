package controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import helper.JSONUtil;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");
        request.setCharacterEncoding("UTF-8");
        String login_json = JSONUtil.getJSON(request);
        User user = JSON.parseObject(login_json, User.class);
        response.setContentType("text/html;charset=UTF-8");
        JSONObject message = new JSONObject();
        try{
            User loggedUser = UserService.getInstance().login(user.getPassword(),user.getUsername());
            if (loggedUser != null){
                message.put("message","登陆成功");
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(2 * 60);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
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
