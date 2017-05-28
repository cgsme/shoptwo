package cn.tutu.web.servlet.front;

import cn.tutu.domain.Cart;
import cn.tutu.domain.CartItem;
import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 购物车
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = new ProductService();

        // 获得商品id
        String pid = request.getParameter("pid");
        // 获得添加到购物车的数量
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // 从session中获得购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 若session中不存在购物车
        if (cart == null) {
            cart = new Cart();   // 创建购物车
        }
        // 获得购物项的Map集合
        Map<String, CartItem> itemMap = cart.getItemMap();
        // 判断map中是否包含了对应的购物项
        if (itemMap.containsKey(pid)) {         // 已经存在该购物项
            // 获得该购物项
            CartItem item = itemMap.get(pid);
            // 设置当前购物项的数量 ： 原来的加上新增的
            item.setQuantity(item.getQuantity() + quantity);
            cart.setTotal(cart.getTotal() + item.getProduct().getShop_price() * quantity);

        } else {    // 不存在当前购物项
            // 通过商品id查询到要添加到购物车的商品
            Product product = productService.findProdInfoByCid(pid);
            CartItem cartItem = new CartItem();   // 创建购物项对象
            cartItem.setProduct(product);    // 设置购物项中的商品
            cartItem.setQuantity(quantity);   // 设置商品数量
            itemMap.put(pid, cartItem);      // 将购物项添加到Map中
            cart.setItemMap(itemMap);         // 将Map添加到购物车中
            cart.setTotal(cart.getTotal() + cartItem.getProduct().getShop_price() * quantity);
        }
        request.getSession().setAttribute("cart", cart);   // 将购物车添加到session中

        // 重定向到购物车页面，不能使用转发（转发地址不变，刷新页面造成重复提交请求）
        response.sendRedirect(request.getContextPath() + "/cart.jsp");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
