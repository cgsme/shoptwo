package cn.tutu.web.servlet.front;

import cn.tutu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 曹贵生 on 2017/5/22.
 * Email: 1595143088@qq.com
 */
@WebServlet("/CheckUserServlet")
public class CheckUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        UserService userService = new UserService();
        boolean result = userService.checkUsername(username);
        String json = "{\"result\" : "+ result + "}";
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
