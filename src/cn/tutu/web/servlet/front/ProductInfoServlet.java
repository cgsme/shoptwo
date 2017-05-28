package cn.tutu.web.servlet.front;

import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 查询商品信息的servlet
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
@WebServlet("/ProductInfoServlet")
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获得页面传过来的商品pid,类别cid,当前页page
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String page = request.getParameter("page");

        ProductService productService = new ProductService();
        Product productInfo = productService.findProdInfoByCid(pid);
        request.setAttribute("productInfo", productInfo);
        request.setAttribute("cid", cid);
        request.setAttribute("page", page);

        String pids = pid;
        StringBuffer sb = null;
        // 获得请求中的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 判断是否是我们需要的cookie
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue(); // 1-2-3
                    String[] pidArr = pids.split("-");
                    // 将数组转为集合
                    List<String> asList = Arrays.asList(pidArr);
                    // 将集合转为LinkedList
                    LinkedList<String> pidList = new LinkedList<String>(asList);
                    // 判断集合中是否已经包含了当前商品id
                    if (pidList.contains(pid)) {  // 已经包含了当前浏览的商品的id
                        pidList.remove(pid);  // 移除当前浏览的商品的id
                        pidList.addFirst(pid);   // 将当前浏览的商品的id放到集合的第一个位置
                    } else {  // 不包含当前浏览的商品的id
                        pidList.addFirst(pid);
                    }
                    sb = new StringBuffer();
                    // 遍历集合中的pid
                    for (int i=0; i<pidList.size()&&i<7; i++) {
                        // 拼接字符串
                        sb.append(pidList.get(i));
                        sb.append("-");   // 最后会多出一个 “-”
                    }
                    // 去掉字符串最后多出的一个 “-”
                    pids = sb.substring(0, sb.length()-1);
                }
            }
        }
        // 创建Cookie对象
        Cookie cookie = new Cookie("pids", pids);
        // 添加cookie
        response.addCookie(cookie);

        request.getRequestDispatcher("/product_info.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
