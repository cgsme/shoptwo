package cn.tutu.web.servlet.front;

import cn.tutu.domain.Cart;
import cn.tutu.domain.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 删除购物项
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
@WebServlet("/DelCartItemServlet")
public class DelCartItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            Map<String, CartItem> itemMap = cart.getItemMap();
            cart.setTotal(cart.getTotal() - itemMap.get(pid).getSubTotal());
            itemMap.remove(pid);
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
