package cn.tutu.web.servlet.front;

import cn.tutu.domain.PageBean;
import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品servlet
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cid = request.getParameter("cid");  // 获得cid
        String currentPage = request.getParameter("page");  // 获得页码
        String index = "";
        ProductService productService = new ProductService();
        PageBean<Product> pageBean = new PageBean<Product>();
        // 每页第一条数据的位置
        if(currentPage == null) {
            index = (pageBean.getCurrentPage() - 1) * pageBean.getPageSize() + "";
        } else {
            index = (Integer.parseInt(currentPage) - 1) * pageBean.getPageSize() + "";
            // 封装pageBean的当前页码
            pageBean.setCurrentPage(Integer.parseInt(currentPage));
        }
        // 查询总记录数
        int totalCount = productService.getTotalCount(cid);
        List<Product> productList = productService.findProductByCid(cid, index);

        // 封装pageBean的总记录数
        pageBean.setTotalCount(totalCount);
        // 封装pageBean中的商品集合
        pageBean.setList(productList);

        // 从cookie中获得浏览记录
        List<Product> pList = new ArrayList<>();   // 用来保存根据cookie中查询到的id所对应的商品信息
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie: cookies) {
                if ("pids".equals(cookie.getName())) {
                    String[] value = cookie.getValue().split("-");
                    for (int i=0; i<value.length; i++) {
                        // 根据cookie中得到的id查询对应的商品信息
                        Product product = productService.findProdInfoByCid(value[i]);
                        pList.add(product);   // 保存到集合中
                    }
                }
            }
        }

        request.setAttribute("cid", cid);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("pList", pList);

        request.getRequestDispatcher("/product_list.jsp").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
