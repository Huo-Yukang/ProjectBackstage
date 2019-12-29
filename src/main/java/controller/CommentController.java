package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Comment;
import helper.JSONUtil;
import service.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

@WebServlet("/comment.ctl")
public class CommentController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String comment_json = JSONUtil.getJSON(request);
        Comment commentToAdd = JSON.parseObject(comment_json,Comment.class);
        JSONObject message = new JSONObject();
        try {
            CommentService.getInstance().add(commentToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
        }
        response.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject message = new JSONObject();
        try {
            Collection<Comment> comments = CommentService.getInstance().findAll();
            String comments_json = JSON.toJSONString(comments);
            response.getWriter().println(comments_json);
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            response.getWriter().println(message);
        }
    }
}
