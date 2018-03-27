package com.tgrajkowski.model.product.bucket;

import java.io.Serializable;

public class ProductBucketPK implements Serializable {
    private long product;
    private long bucket;

    public ProductBucketPK() {
    }

    public ProductBucketPK(long product, long bucket) {
        this.product = product;
        this.bucket = bucket;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getBucket() {
        return bucket;
    }

    public void setBucket(long bucket) {
        this.bucket = bucket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductBucketPK)) return false;

        ProductBucketPK that = (ProductBucketPK) o;

        if (product != that.product) return false;
        return bucket == that.bucket;
    }

    @Override
    public int hashCode() {
        int result = (int) (product ^ (product >>> 32));
        result = 31 * result + (int) (bucket ^ (bucket >>> 32));
        return result;
    }
}
