package cn.tutu.service;

import cn.tutu.dao.OrderDao;
import cn.tutu.domain.Order;
import cn.tutu.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 订单业务层
 *
 * Created by 曹贵生 on 2017/5/25. 
 * Email: 1595143088@qq.com
 */
public class OrderService {

    private OrderDao orderDao = new OrderDao();

    /**
     * 保存订单
     * @param order
     */
    public void saveOrder(Order order) {
        try {
            // 开启事务
            DataSourceUtils.startTransaction();
            // order表中的oid是orderItem表中的外键，需要先保存order中的数据，才能保存orderItem中的数据，否则报错
            orderDao.saveOrder(order);
            orderDao.saveOrderItem(order);
        } catch (SQLException e) {
            try {
                // 异常回滚事务
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // 提交事务和释放资源
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新收货人信息
     * @param order
     */
    public void updateRecMsg(Order order) {

        try {
            orderDao.updateRecMsg(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 更新订单状态
     * @param r6_order
     */
    public void updateOrderState(String r6_order) {
        try {
            orderDao.updateOrderState(r6_order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定用户所有订单
     * @param uid
     * @return
     */
    public List<Order> findAllOrders(String uid) {
        List<Order> allOrders = null;
        try {
            allOrders = orderDao.findAllOrders(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrders;
    }

    /**
     * 查询订单项和商品所需数据
     * @param oid
     * @return
     */
    public List<Map<String,Object>> findAllOrderItemsAndProducts(String oid) {
        List<Map<String, Object>> allOrderItemsAndProducts = null;
        try {
            allOrderItemsAndProducts = orderDao.findAllOrderItemsAndProducts(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allOrderItemsAndProducts;
    }

    /**
     * 后台查询所有订单
     * @return
     */
    public List<Order> findAllOrders() {
        try {
            return orderDao.findAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据商品id查询商品详情
     * @param oid
     * @return
     */
    public List<Map<String, Object>> findOrderInfoByOid(String oid) {
        try {
            return orderDao.findOrderInfoByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
