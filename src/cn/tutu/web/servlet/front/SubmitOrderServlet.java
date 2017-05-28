package cn.tutu.web.servlet.front;

import cn.tutu.domain.*;
import cn.tutu.service.OrderService;
import cn.tutu.utils.CommonsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 提交订单
 *
 * Created by 曹贵生 on 2017/5/25.
 * Email: 1595143088@qq.com
 */
@WebServlet("/SubmitOrderServlet")
public class SubmitOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        // 判断用户是否登录
        if (null == user) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 判断购物车是否为空
        if (cart == null) {
            response.sendRedirect(request.getContextPath() + "/CartServlet");
            return;
        }
        // 封装订单对象
        Order order = new Order();
        order.setOid(CommonsUtils.getUUID());   // id
        order.setTotal(cart.getTotal());   // 总计
        order.setOrdertime(new Date());    // 下单日期
        order.setState(0);   // 0：未付款  1：已经付款
        order.setUser(user);    // 所属用户
        order.setName(null);       // 收货人姓名
        order.setAddress(null);     // 收货地址
        order.setTelephone(null);     // 收货人电话

        // 购物车中循环拿到购物项
        Map<String, CartItem> cartItemMap = cart.getItemMap();
        for (CartItem cartItem :cartItemMap.values()) {
            // 封装订单条目
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());    // 订单项中的商品
            orderItem.setSubtotal(cartItem.getSubTotal());   //  订单项小计
            orderItem.setItemid(CommonsUtils.getUUID());     //  订单id
            orderItem.setCount(cartItem.getQuantity());      // 数量
            orderItem.setOrder(order);           // 将order设置给orderItem
            order.getOrderItemList().add(orderItem);         // 订单中所有订单项
        }
        // 创建OrderService对象
        OrderService orderService = new OrderService();
        // 报存订单相关信息
        orderService.saveOrder(order);
        // 将订单保存到session中
        request.getSession().setAttribute("order", order);

        // 清理购物车

        // 重定向到订单页面
        response.sendRedirect(request.getContextPath() + "/order_info.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
