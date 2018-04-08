package com.tgrajkowski.model.product.mark;

import java.io.Serializable;

public class ProductMarkPK implements Serializable {
    private long product;
    private long user;

    public ProductMarkPK() {
    }

    public ProductMarkPK(long product, long user) {
        this.product = product;
        this.user = user;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMarkPK)) return false;

        ProductMarkPK that = (ProductMarkPK) o;

        if (product != that.product) return false;
        return user == that.user;
    }

    @Override
    public int hashCode() {
        int result = (int) (product ^ (product >>> 32));
        result = 31 * result + (int) (user ^ (user >>> 32));
        return result;
    }
}
