package cn.tutu.dao;

import cn.tutu.domain.Order;
import cn.tutu.domain.OrderItem;
import cn.tutu.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 订单Dao层
 *
 * Created by 曹贵生 on 2017/5/25.
 * Email: 1595143088@qq.com
 */
public class OrderDao {

    private QueryRunner queryRunner = new QueryRunner();
    /**
     * 保存订单条目
     * @param order
     */
    public void saveOrderItem(Order order) throws SQLException {
        // 获得连接
        Connection conn = DataSourceUtils.getConnection();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        // 循环遍历订单项集合，保存
        for (OrderItem orderItem : order.getOrderItemList()) {
            System.out.println(orderItem.toString());
            queryRunner.update(conn, sql, orderItem.getItemid(), orderItem.getCount(),
                    orderItem.getSubtotal(), orderItem.getProduct().getPid(), orderItem.getOrder().getOid());
        }
    }

    /**
     * 保存订单
     * @param order
     */
    public void saveOrder(Order order) throws SQLException {
        Connection conn = DataSourceUtils.getConnection();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        queryRunner.update(conn, sql, order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(),
                order.getName(), order.getTelephone(), order.getUser().getUid());
    }

    /**
     * 更新收货人信息
     * @param order
     */
    public void updateRecMsg(Order order) throws SQLException {
        String sql = "update orders set address = ?, name = ?, telephone = ? where oid = ?";
        queryRunner.update(DataSourceUtils.getConnection(),sql, order.getAddress(), order.getName(),
                order.getTelephone(), order.getOid());
    }

    /**
     * 更新订单状态
     * @param r6_order
     */
    public void updateOrderState(String r6_order) throws SQLException {
        String sql = "update orders set state = 1 where oid = ?";
        queryRunner.update(DataSourceUtils.getConnection(), sql, r6_order);
    }


    /**
     * 查询用户的所有订单
     * @param uid
     * @return
     */
    public List<Order> findAllOrders(String uid) throws SQLException {
        QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders where uid = ?";
        return query.query(sql, new BeanListHandler<>(Order.class), uid);
    }

    /**
     * 查询订单项和商品所需字段信息
     * @param oid
     * @return
     */
    public List<Map<String,Object>> findAllOrderItemsAndProducts(String oid) throws SQLException {
        QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select i.count, i.subtotal, p.pimage, p.pname, p.shop_price from orderitem i, product p " +
                "where i.pid = p.pid and i.oid = ?";
        return query.query(sql, new MapListHandler(), oid);
    }

    /**
     * 后台查询所有订单
     * @return
     */
    public List<Order> findAllOrders() throws SQLException {
        QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders";
        return query.query(sql, new BeanListHandler<>(Order.class));
    }

    /**
     * 根据商品详情查询商品信息
     * @param oid
     * @return
     */
    public List<Map<String,Object>> findOrderInfoByOid(String oid) throws SQLException {
        QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select p.pimage, p.pname, p.shop_price, i.count, i.subtotal from product p, orderitem i " +
                "where p.pid = i.pid and oid = ?";
        return query.query(sql, new MapListHandler(), oid);
    }
}
