package com.tgrajkowski.model.product;

import java.io.Serializable;

public class ProductBoughtPK implements Serializable{
    private long product;
    private long productsOrder;

    public ProductBoughtPK() {
    }

    public ProductBoughtPK(long product, long productsOrder) {
        this.product = product;
        this.productsOrder = productsOrder;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(long productsOrder) {
        this.productsOrder = productsOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductBoughtPK)) return false;

        ProductBoughtPK that = (ProductBoughtPK) o;

        if (product != that.product) return false;
        return productsOrder == that.productsOrder;
    }

    @Override
    public int hashCode() {
        int result = (int) (product ^ (product >>> 32));
        result = 31 * result + (int) (productsOrder ^ (productsOrder >>> 32));
        return result;
    }
}
