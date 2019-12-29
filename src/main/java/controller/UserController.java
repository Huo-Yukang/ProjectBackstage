package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.User;
import helper.JSONUtil;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JSONUtil.getJSON(request);
        User userToAdd = JSON.parseObject(user_json,User.class);
        JSONObject message = new JSONObject();
        try {
            UserService.getInstance().add(userToAdd);
            message.put("message", "注册成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "该用户已被注册");
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
        }
        response.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        try {
            if (id_str == null) {
                Collection<User> users = UserService.getInstance().findAll();
                String user_json = JSON.toJSONString(users, SerializerFeature.DisableCircularReferenceDetect);
                response.getWriter().println(user_json);
            } else {
                int id = Integer.parseInt(id_str);
                User user= UserService.getInstance().find(id);
                String user_json = JSON.toJSONString(user);
                response.getWriter().println(user_json);
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
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user_json = JSONUtil.getJSON(request);
        User userToAdd = JSON.parseObject(user_json,User.class);
        JSONObject message = new JSONObject();
        try {
            UserService.getInstance().update(userToAdd);
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
}
