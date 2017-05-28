package cn.tutu.web.servlet.front;

import cn.tutu.domain.Order;
import cn.tutu.domain.OrderItem;
import cn.tutu.domain.Product;
import cn.tutu.domain.User;
import cn.tutu.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 我的订单
 *
 * Created by 曹贵生 on 2017/5/25.
 * Email: 1595143088@qq.com
 */
@WebServlet("/MyOrdersServlet")
public class MyOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        OrderService orderService = new OrderService();
        // 查询所有订单
        List<Order> orderList = orderService.findAllOrders(user.getUid());
        if (orderList != null) {
            // 遍历订单集合
            for (Order order : orderList) {
                // 查询订单项和商品
                List<Map<String, Object>> mapList = orderService.findAllOrderItemsAndProducts(order.getOid());
                // 判断集合是否为空
                if (mapList != null) {
                    // 循环遍历Map，得到每一个字段
                    for (Map<String, Object> map : mapList) {
                        try {
                            // 使用 Beanutils封装订单项
                            OrderItem orderItem = new OrderItem();
                            BeanUtils.populate(orderItem, map);
                            // 使用beanUtils封装商品
                            Product product = new Product();
                            BeanUtils.populate(product, map);
                            // 将商品封装到订单项中
                            orderItem.setProduct(product);
                            // 将订单项封装到订单中
                            order.getOrderItemList().add(orderItem);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        // 将数据存入request中
        request.setAttribute("orderList", orderList);
        // 转发到我的订单页面
        request.getRequestDispatcher("/order_list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
