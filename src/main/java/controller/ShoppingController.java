package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.Shopping;
import helper.JSONUtil;
import service.ShoppingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/ShoppingController")
public class ShoppingController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shopping_json = JSONUtil.getJSON(request);
        Shopping shoppingToAdd = JSON.parseObject(shopping_json,Shopping.class);
        JSONObject message = new JSONObject();

        try {
            ShoppingService.getInstance().add(shoppingToAdd);
            message.put("message","已加入购物车");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id_str = request.getParameter("user_id");
        JSONObject message = new JSONObject();
        int user_id = Integer.parseInt(user_id_str);
        try {
            responseShoppingsByUser_id(user_id,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void responseShoppingsByUser_id(Integer id,HttpServletResponse response) throws SQLException, IOException {
        Collection<Shopping> shoppings = ShoppingService.getInstance().findByUser_id(id);
        String shopping_json = JSON.toJSONString(shoppings,SerializerFeature.DisableCircularReferenceDetect);
        response.getWriter().println(shopping_json);
    }
}
