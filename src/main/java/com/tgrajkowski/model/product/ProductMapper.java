package com.tgrajkowski.model.product;

import com.tgrajkowski.model.bucket.ProductBucketDto;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public Product mapToProduct(ProductDto productDto) {
        Product product = new Product(
                productDto.getPrice(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getImageLink());
        return product;
    }

    public Product mapToProductWithId(ProductDto productDto) {
        Product product = new Product(
                productDto.getPrice(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getImageLink());
        product.setId(productDto.getId());
        return product;
    }

    public ProductDto mapToProductDto(Product product) {
        int amountCounter = 0;
        int bookedProducts = 0;

        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getPrice(),
                product.getTitle(),
                product.getDescription(),
                product.getImageLink()
        );

        List<ProductAmount> amountList = new ArrayList<>();
        List<ProductAmount> amountReceived = product.getProductAmounts();
        List<Long> idAmountList = new ArrayList<>();

        for (ProductAmount amount : amountReceived) {
            ProductAmount productAmount = new ProductAmount();
            productAmount.setId(amount.getId());
            amountList.add(productAmount);
            amountCounter++;
            if (amount.getBucket()!=null){
                bookedProducts++;
            }

            Long id = amount.getId();
            idAmountList.add(id);
        }
        productDto.setAmountList(amountList);
        productDto.setAmount(amountCounter);
        productDto.setBookedProduct(bookedProducts);
        productDto.setIdAmountList(idAmountList);
        return productDto;
    }


    public List<ProductDto> mapToProductDtoList(List<Product> products) {
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = mapToProductDto(product);
            dtoList.add(productDto);
        }
        return dtoList;
    }

    public ProductBucketDto maptoProcuctBucketDto(Product product){
        ProductBucketDto bucketDto = new ProductBucketDto();
        bucketDto.setId(product.getId());
        bucketDto.setAmount(0);
        bucketDto.setDescription(product.getDescription());
        bucketDto.setImageLink(product.getImageLink());
        bucketDto.setPrice(product.getPrice());
        bucketDto.setTitle(product.getTitle());
        return bucketDto;
    }

}
