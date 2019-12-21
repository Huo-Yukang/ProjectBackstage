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

@WebServlet("/User.ctl")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        User userToAdd = JSON.parseObject(user_json,User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            UserService.getInstance().add(userToAdd);
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        String name_str = request.getParameter("name");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            if (id_str == null && name_str == null) {
                //获得所有事务
                Collection<User> users = UserService.getInstance().findAll();
                String user_json = JSON.toJSONString(users, SerializerFeature.DisableCircularReferenceDetect);
                //响应food_json到前端
                response.getWriter().println(user_json);
            } else if(id_str != null && name_str == null){
                int id = Integer.parseInt(id_str);
                //根据id查找学院
                User user= UserService.getInstance().find(id);
                String user_json = JSON.toJSONString(user);
                //响应school_json到前端
                response.getWriter().println(user_json);
            }else{
                User user = UserService.getInstance().findToUsername(name_str);
                String food_json = JSON.toJSONString(user);
                //响应school_json到前端
                response.getWriter().println(food_json);
            }
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        User userToAdd = JSON.parseObject(user_json,User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
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
        //响应message到前端
        response.getWriter().println(message);
    }
}
