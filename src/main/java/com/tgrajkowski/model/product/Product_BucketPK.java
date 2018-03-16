package com.tgrajkowski.model.product;

import java.io.Serializable;

public class Product_BucketPK implements Serializable {
    protected Long productIdA;
    protected Long bucketIdA;

    public Product_BucketPK() {
    }

    public Product_BucketPK(Long productIdA, Long bucketIdA) {
        this.productIdA = productIdA;
        this.bucketIdA = bucketIdA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product_BucketPK)) return false;

        Product_BucketPK that = (Product_BucketPK) o;

        if (productIdA != null ? !productIdA.equals(that.productIdA) : that.productIdA != null) return false;
        return bucketIdA != null ? bucketIdA.equals(that.bucketIdA) : that.bucketIdA == null;
    }

    @Override
    public int hashCode() {
        int result = productIdA != null ? productIdA.hashCode() : 0;
        result = 31 * result + (bucketIdA != null ? bucketIdA.hashCode() : 0);
        return result;
    }
}
