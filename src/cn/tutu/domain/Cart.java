package cn.tutu.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
public class Cart {

    private double total;
    private Map<String, CartItem> itemMap = new HashMap<>();

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Map<String, CartItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, CartItem> itemMap) {
        this.itemMap = itemMap;
    }
}
