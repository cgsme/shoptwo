package cn.tutu.dao;

import cn.tutu.domain.User;
import cn.tutu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * 用户dao
 *
 * Created by 曹贵生 on 2017/5/21.
 * Email: 1595143088@qq.com
 */
public class UserDao {

    QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
    /**
     * 注册
     * @param user
     * @return
     * @throws SQLException
     */
    public int register(User user) throws SQLException {

        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        int update = queryRunner.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
                user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(),
                user.getCode());
        return update;
    }

    /**
     * 激活
     * @param code
     * @return
     * @throws SQLException
     */
    public int active(String code) throws SQLException {
        String sql = "update user set state = ? where code = ?";
        int update = queryRunner.update(sql, 1, code);
        return update;
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    public long checkUsername(String username) throws SQLException {

        String sql = "select count(*) from user where username = ?";
        long result = (long) queryRunner.query(sql, new ScalarHandler(), username);
        return  result;
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public User login(String username, String password) throws SQLException {
        String sql = "select * from user where username = ? and password = ? and state = 1";
        return queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
    }
}
