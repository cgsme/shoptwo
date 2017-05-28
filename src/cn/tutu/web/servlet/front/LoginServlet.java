package cn.tutu.web.servlet.front;

import cn.tutu.domain.User;
import cn.tutu.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 登录
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 封装表单数据到User对象中
            String autoLogin = request.getParameter("autoLogin");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            UserService userService = new UserService();
            User loginUser = null;

            // 判断用户名密码是否为空
            if (!"".equals(username) && null != username && !"".equals(password) && null != password) {
                loginUser = userService.login(username, password);
            }

            // 判断是否存在当前用户
            if (loginUser == null) {
                request.setAttribute("msg", "用户名或密码错误~");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                // 将用户数据保存到session中
                request.getSession().setAttribute("user", loginUser);

                // 判断用户是否勾选了自动登录
                if (!"".equals(autoLogin) && null != autoLogin && "autoLogin".equals(autoLogin)) {   // 勾选了自动登录
                    // 将用户名和密码保存到cookie中
                    Cookie cookie = new Cookie("login_name", loginUser.getUsername());
                    cookie.setMaxAge(10*60);
//                    cookie.setPath(request.getContextPath());
                    Cookie cookie2 = new Cookie("login_password", loginUser.getPassword());
                    cookie2.setMaxAge(10*60);
//                    cookie2.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    response.addCookie(cookie2);
                }
                // 重定向到首页
                response.sendRedirect(request.getContextPath());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
