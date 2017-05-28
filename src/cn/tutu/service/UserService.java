package cn.tutu.service;

import cn.tutu.dao.UserDao;
import cn.tutu.domain.User;

import java.sql.SQLException;

/**
 * 用户service
 *
 * Created by 曹贵生 on 2017/5/21.
 * Email: 1595143088@qq.com
 */
public class UserService {

    private UserDao userDao = new UserDao();

    /**
     * 注册
     * @param user
     * @return
     */
    public boolean register(User user) {

        int register = 0;
        try {
            register = userDao.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return register > 0;
    }

    /**
     * 激活
     * @param code
     * @return
     */
    public boolean active(String code) {
        int result = 0;
        try {
            result = userDao.active(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * 校验用户名是否存在
     * @param username
     * @return
     */
    public boolean checkUsername(String username) {
        UserDao userDao = new UserDao();
        long result = 0;
        try {
            result = userDao.checkUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        User loginUser = null;
        try {
            loginUser = userDao.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  loginUser;
    }
}
