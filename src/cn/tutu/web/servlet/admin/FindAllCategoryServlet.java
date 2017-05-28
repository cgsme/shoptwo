package cn.tutu.web.servlet.admin;

import cn.tutu.domain.Category;
import cn.tutu.domain.Product;
import cn.tutu.service.ProductService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 跳转到添加商品的页面
 *
 * Created by 曹贵生 on 2017/5/26.
 * Email: 1595143088@qq.com
 */
@WebServlet("/admin/FindAllCategoryServlet")
public class FindAllCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setCharacterEncoding("utf-8");
        //准备商品类别的数据
        ProductService productService = new ProductService();
        List<Category> categorytList = productService.findAllCategory();
        //将数组转成json对象
        Gson gson = new Gson();
        String json = gson.toJson(categorytList);
        //写回数据
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
