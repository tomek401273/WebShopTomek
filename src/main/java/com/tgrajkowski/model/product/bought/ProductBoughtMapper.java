package com.tgrajkowski.model.product.bought;

import com.tgrajkowski.model.product.ProductMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductBoughtMapper {
    private ProductMapper productMapper = new ProductMapper();

    public ProductBoughtDto mapToProductBoughtDto(ProductBought productBought) {
       return new ProductBoughtDto(
                productMapper.mapToProductDto2(productBought.getProduct()),
                productBought.getAmount()
        );
    }
    public List<ProductBoughtDto> mapToProductBoughtDtoList (List<ProductBought> productBoughts) {
        List<ProductBoughtDto> mappedProductBoughts = new ArrayList<>();
        for (ProductBought productBought: productBoughts) {
            mappedProductBoughts.add(mapToProductBoughtDto(productBought));
        }

        return mappedProductBoughts;
    }
}
