package cn.tutu.dao;

import cn.tutu.domain.Category;
import cn.tutu.domain.Product;
import cn.tutu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 商品dao
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
public class ProductDao {

    QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 查询最新商品
     * @return
     */
    public List<Product> findNewProduct() throws SQLException {
        String sql = "select * from product order by pdate desc limit 0,9 ";
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 查询热门商品
     * @return
     */
    public List<Product> findHotProduct() throws SQLException {
        String sql = "select * from product where is_hot = 1 limit 0,9";
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 获得指定cid对应商品总记录数
     * @param cid
     * @return
     */
    public int getTotalCount(String cid) throws SQLException {
        String sql = "select count(*) from product where cid = ?";
        Long count = (Long) queryRunner.query(sql, new ScalarHandler(), cid);
        return count.intValue();
    }

    /**
     * 根据类别查询商品   limit 0,6    从第1行开始的    显示6条数据    初始记录是0
     * @return
     */
    public List<Product> findProductByCid(String cid, String index) throws SQLException {
        String sql = "select * from product where cid = ? limit ?,12";
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, index);
    }

    /**
     * 根据商品id查询商品详细信息
     * @param pid
     * @return
     */
    public Product findProdInfoByCid(String pid) throws SQLException {
        String sql = "select * from product where pid = ?";
        return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
    }

    /**
     * 查询所有类别
     * @return
     */
    public List<Category> findAllCategory() throws SQLException {
        String sql = "select * from category";
        List<Category> query = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
        return query;
    }

    /**
     * 保存商品对象
     * @param product
     */
    public void saveProduct(Product product) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        queryRunner.update(sql, product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
                product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),
                product.getCategory().getCid());
    }
}
