package cn.tutu.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 订单项
 *
 * Created by 曹贵生 on 2017/5/25.
 * Email: 1595143088@qq.com
 */
public class OrderItem {
    private String itemid;
    private Integer count;
    private double subtotal;
    private Product product;
    private Order order;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid='" + itemid + '\'' +
                ", count=" + count +
                ", subtotal=" + subtotal +
                ", product=" + product +
                ", order=" + order +
                '}';
    }

}
