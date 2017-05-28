package cn.tutu.service;

import cn.tutu.dao.ProductDao;
import cn.tutu.domain.Category;
import cn.tutu.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * 商品service
 *
 * Created by 曹贵生 on 2017/5/23.
 * Email: 1595143088@qq.com
 */
public class ProductService {

    ProductDao productDao = new ProductDao();

    /**
     * 查询最新商品
     * @return
     */
    public List<Product> findNewProduct() {
        List<Product> newProduct = null;
        try {
            newProduct = productDao.findNewProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newProduct;
    }

    /**
     * 查询热门商品
     * @return
     */
    public List<Product> finHotProduct() {
        List<Product> hotProduct = null;
        try {
            hotProduct = productDao.findHotProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotProduct;
    }

    /**
     * 获得指定cid的总记录数
     * @param cid
     * @return
     */
    public int getTotalCount(String cid) {
        int count = 0;
        try {
            count = productDao.getTotalCount(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 根据id查询所有商品
     * @param cid
     * @return
     */
    public List<Product> findProductByCid(String cid, String index) {
        List<Product> productList = null;
        try {
            productList = productDao.findProductByCid(cid, index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * 根据cid查询商品详细信息
     *
     * @param pid
     * @return
     */
    public Product findProdInfoByCid(String pid) {
        Product productInfo = null;
        try {
            productInfo = productDao.findProdInfoByCid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productInfo;
    }

    /**
     * 查询所有类别
     * @return
     */
    public List<Category> findAllCategory() {

        try {
            return productDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 报错商品信息
     * @param product
     */
    public void saveProduct(Product product) {
        try {
            productDao.saveProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
