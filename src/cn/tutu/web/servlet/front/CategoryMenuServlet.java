package cn.tutu.web.servlet.front;

import cn.tutu.domain.Category;
import cn.tutu.service.CategoryService;
import cn.tutu.utils.JedisUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 类别
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
@WebServlet(value = "/CategoryMenuServlet")
public class CategoryMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 先从redis缓存中取数据若没有数据在查询数据库
        Jedis jedis = JedisUtils.getJedis();
        String redisList = jedis.get("categoryList");
        if(redisList == null) {
            System.out.println("缓存中没有数据");
            System.out.println(redisList);
            /*
             * 查询所有类别
             */
            CategoryService categoryService = new CategoryService();
            List<Category> categoryList = categoryService.findAllCategory();
            Gson gson = new Gson();
            redisList = gson.toJson(categoryList);
            jedis.set("categoryList", redisList);
        } else {
            System.out.println("缓存中有数据");
            System.out.println(redisList);
        }

        response.getWriter().write(redisList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
