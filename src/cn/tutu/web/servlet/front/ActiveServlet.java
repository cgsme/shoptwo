package cn.tutu.web.servlet.front;

import cn.tutu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户激活
 *
 * Created by 曹贵生 on 2017/5/22.
 * Email: 1595143088@qq.com
 */
@WebServlet("/ActiveServlet")
public class ActiveServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserService userService = new UserService();
        boolean isSuccess = userService.active(code);

        if(isSuccess) {
            request.setAttribute("msg", "恭喜你，成功激活~");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", "激活失败...");
            request.getRequestDispatcher("/msg.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
