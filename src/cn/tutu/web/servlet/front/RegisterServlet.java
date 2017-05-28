package cn.tutu.web.servlet.front;

import cn.tutu.domain.User;
import cn.tutu.service.UserService;
import cn.tutu.utils.CommonsUtils;
import cn.tutu.utils.SimpleMailUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 注册servlet
 *
 * Created by 曹贵生 on 2017/5/21.
 * Email: 1595143088@qq.com
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置编码
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        User user = new User();
        try {

            // user中birthday字段是date类型，BeanUtils无法将字符串转成Date，所以要注册一个类型转换器
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class aClass, Object value) {
                    // value为要转换的值
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }, Date.class);

            // 将字段封装到user对象中
            BeanUtils.populate(user, requestParameterMap);

            // 设置id（通过uuid）
            user.setUid(CommonsUtils.getUUID());
            // 设置手机
            user.setTelephone(null);
            // 设置状态
            user.setState(0);  // 未激活状态
            // 设置激活码
            user.setCode(CommonsUtils.getUUID());

            // 调用service
            UserService userService = new UserService();
            boolean isRegister = userService.register(user);
            if(isRegister) {
                SimpleMailUtils.sendMail(user.getEmail(), user.getCode());
                response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
