package cn.tutu.web.servlet.front;

import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 首页的servlet
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 实例化service
        ProductService productService = new ProductService();
        // 查询最新商品
        List<Product> newList = productService.findNewProduct();
        // 查询最热商品
        List<Product> hotList = productService.finHotProduct();

        request.setAttribute("newProList", newList);
        request.setAttribute("hotProList", hotList);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
