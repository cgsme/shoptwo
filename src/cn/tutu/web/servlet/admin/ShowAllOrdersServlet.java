package cn.tutu.web.servlet.admin;

import cn.tutu.domain.Order;
import cn.tutu.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台查询所有订单
 *
 * Created by 曹贵生 on 2017/5/27.
 * Email: 1595143088@qq.com
 */
@WebServlet("/admin/ShowAllOrdersServlet")
public class ShowAllOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        List<Order> orderList =  orderService.findAllOrders();
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
