package cn.tutu.domain;

/**
 * 购物车条目
 *
 * Created by 曹贵生 on 2017/5/24.
 * Email: 1595143088@qq.com
 */
public class CartItem {

    private Product product;
    private int quantity;
    private double subTotal;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return product.getShop_price() * quantity;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
