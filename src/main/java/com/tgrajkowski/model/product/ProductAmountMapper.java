package com.tgrajkowski.model.product;

public class ProductAmountMapper {
    public ProductAmountDto maptoProductAmountDto(ProductAmount productAmount){
        ProductAmountDto dto = new ProductAmountDto();
        dto.setId(productAmount.getId());
        return null;
    }
}
