package cn.tutu.dao;

import cn.tutu.domain.Category;
import cn.tutu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
public class CategoryDao {


    QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 查询所有类别
     * @return
     * @throws SQLException
     */
    public List<Category> findAllCategory() throws SQLException {
        String sql = "select * from category";
        return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
    }
}
