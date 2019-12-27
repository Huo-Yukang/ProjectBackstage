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

@WebServlet("/shopping.ctl")
public class ShoppingController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shopping_json = JSONUtil.getJSON(request);
        Shopping shopping = JSON.parseObject(shopping_json,Shopping.class);
        JSONObject message = new JSONObject();

        try {
            boolean addToShopping = ShoppingService.getInstance().add(shopping);
            if (addToShopping){
                message.put("message","已加入购物车");
            }else {
                message.put("message","该食物已售罄");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        int user_id = Integer.parseInt(id_str);
        try {
            responseShoppingsByUser_id(user_id,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shopping_id_str = request.getParameter("id");
        JSONObject message = new JSONObject();
        int shopping_id = Integer.parseInt(shopping_id_str);
        try {
            ShoppingService.getInstance().delete(shopping_id);
            //响应数据
            message.put("message","已经移出购物车");
            response.getWriter().println(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id_str = request.getParameter("id");
        int user_id = Integer.parseInt(user_id_str);
        JSONObject message = new JSONObject();
        try {
            boolean traned = ShoppingService.getInstance().transaction(user_id);
            if(traned == true){
                message.put("message","支付成功，欢迎再次选购");
            }else {
                message.put("message","支付失败，余额不足");
            }
            response.getWriter().println(message);
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
