package cn.tutu.web.filter;

import cn.tutu.domain.User;
import cn.tutu.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自动登录过滤器
 *
 * Created by 曹贵生 on 2017/5/26.
 * Email: 1595143088@qq.com
 */
@WebFilter(value = "/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    /**
     * 执行过过滤方法
     *
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 判断用户是否已经登录
        if (request.getSession().getAttribute("user") == null) {   // 未登录
            Cookie[] cookies = request.getCookies();
            String username = "";
            String password = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("login_name".equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                    if ("login_password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }
            UserService userService = new UserService();
            User loginUser = userService.login(username, password);
            if (null != loginUser) {
                request.getSession().setAttribute("user", loginUser);  // 保存用户登录信息
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
