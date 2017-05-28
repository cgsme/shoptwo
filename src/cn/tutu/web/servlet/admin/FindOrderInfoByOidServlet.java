package cn.tutu.web.servlet.admin;

import cn.tutu.service.OrderService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 根据订单编号查询订单详情
 *
 * Created by 曹贵生 on 2017/5/27.
 * Email: 1595143088@qq.com
 */
@WebServlet("/admin/FindOrderInfoByOidServlet")
public class FindOrderInfoByOidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 模仿网络延迟，进程休息2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.setCharacterEncoding("utf-8");
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderService();
        List<Map<String, Object>> mapList = orderService.findOrderInfoByOid(oid);
        Gson gson = new Gson();
        String json = gson.toJson(mapList);
        System.out.println(json);
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
