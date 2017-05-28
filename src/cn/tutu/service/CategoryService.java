package cn.tutu.service;

import cn.tutu.dao.CategoryDao;
import cn.tutu.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 类别
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
public class CategoryService {

    CategoryDao categoryDao = new CategoryDao();

    /**
     * 查询所有类别
     * @return
     */
    public List<Category> findAllCategory() {
        List<Category> allCategory = null;
        try {
            allCategory = categoryDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCategory;
    }
}
