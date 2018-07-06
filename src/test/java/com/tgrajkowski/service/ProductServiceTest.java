package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.comment.Comment;
import com.tgrajkowski.model.product.comment.CommentDto;
import com.tgrajkowski.model.product.mark.ProductMark;
import com.tgrajkowski.model.product.mark.ProductMarkDto;
import com.tgrajkowski.model.product.reminder.ProductEmailReminder;
import com.tgrajkowski.model.product.reminder.ProductEmailReminderDto;
import com.tgrajkowski.model.user.Users;
import org.junit.Assert;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @InjectMocks
    ProductService productService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductBucketDao productBucketDao;

    @Mock
    private BucketDao bucketDao;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private ProductStatusDao productStatusDao;

    @Mock
    private ProductEmailReminderDao productEmailReminderDao;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ProductMarkDao productMarkDao;

    @Mock
    private ShortDescriptionDao shortDescriptionDao;

    private Long productId = new Long(1);

    @Mock
    private ProductMapper productMapper;

    @Before
    public void init() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("login");
    }

    @Test
    public void checkAvailableShouldReturnProduct() {
        // Given
        Product product = new Product();
        product.setId((long) 1);
        product.setAvailableAmount(2);
        when(productDao.findById((long) 1)).thenReturn(product);
        // When
        int availableProduct = productService.checkAvailable(productId);
        // Then
        assertEquals(2, availableProduct);
    }

    @Test
    public void checkAvailableProductNotExist() {
        // Given
        when(productDao.findById((long) 1)).thenReturn(null);
        // When
        int available = productService.checkAvailable(productId);
        // Then
        assertEquals(-1, available);
    }

    @Test
    public void checkAvailableProductNotAvailable() {
        // Given
        Product product = new Product();
        product.setId(productId);
        product.setAvailableAmount(0);
        when(productDao.findById((long) 1)).thenReturn(product);
        // When
        int available = productService.checkAvailable(productId);
        // Then
        assertEquals(0, available);
    }

    @Test
    public void shouldGetProducts() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.getProductOnSaleAndInaccesiableAsc()).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);
        // When
        List<ProductDto> retrieved = productService.getProducts();
        // Then
        assertEquals(3, retrieved.size());
    }

    @Test
    public void shouldGetEmptyProductList() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.getProductOnSaleAndInaccesiableAsc()).thenReturn(productList);
        // When
        List<ProductDto> retrieved = productService.getProducts();
        // Then
        assertEquals(0, retrieved.size());
    }

    @Test
    public void shouldGetProductToEdit() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.findAll()).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);
        // When
        List<ProductDto> retrieved = productService.getProductsToEdit();
        // Then
        assertEquals(3, retrieved.size());
    }

    @Test
    public void shouldRemoveProductDatabase() {
        // Given
        Product product = createProductToRemowe();
        when(productDao.findById((long) 1)).thenReturn(product);
        when(bucketDao.save(product.getProductBuckets().get(0).getBucket())).thenReturn(null);
        ProductBucketPK productBucketPK = new ProductBucketPK();
        productBucketPK.setBucket((long) 1);
        productBucketPK.setProduct((long) 1);
        when(productDao.findById((long) 1)).thenReturn(product);
        doNothing().when(productBucketDao).delete(productBucketPK);
        when(productDao.save(product)).thenReturn(null);
        ProductDto productDto = new ProductDto();
        when(productMapper.mapToProductDtoForMail(product)).thenReturn(productDto);
        // When
        productService.removeProductFromDatabase((long) 1);
        // Then
        assertEquals(2, product.getAvailableAmount());
        assertEquals(1, product.getProductBuckets().size());
        Assert.assertTrue(product.isToDelete());
    }

    @Test
    public void shouldDeleteProductFromSale() {
        // Given
        List<Product> productList = new ArrayList<>();
        Product product = createProductToRemowe();
        product.setToDelete(true);
        productList.add(product);
        when(productDao.findByToDelete(true)).thenReturn(productList);
        ProductStatus productStatus = new ProductStatus("withdrawn", "Product is withdrawn form sale");
        when(productStatusDao.findProductStatusByCode("withdrawn")).thenReturn(productStatus);
        when(productDao.save(product)).thenReturn(null);
        // When
        productService.deleteProductFromSale();
        // Then
        assertEquals(false, product.isToDelete());
        assertEquals("withdrawn", product.getStatus().getCode());
        assertEquals("Product is withdrawn form sale", product.getStatus().getName());
    }

    @Test
    public void updateProductInit() {
        // Given
        Product product = new Product();
        product.setId(productId);
        ProductStatus productStatus = new ProductStatus("sale", "Product is on sale");
        product.setStatus(productStatus);
        when(productDao.findById(productId)).thenReturn(product);

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setStatusCode("withdrawn");

        ProductStatus productStatusWithdrawn = new ProductStatus();
        productStatusWithdrawn.setId((long) 2);
        productStatusWithdrawn.setCode("withdrawn");
        when(productStatusDao.findProductStatusByCode("withdrawn")).thenReturn(productStatusWithdrawn);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setTitle("title");
        updatedProduct.setPrice(new BigDecimal(100));
        updatedProduct.setImageLink("image");
        updatedProduct.setAvailableAmount(10);
        updatedProduct.setTotalAmount(10);
        updatedProduct.setDescription("description");
        List<ShortDescription> shortDescriptions = new ArrayList<>();
        ShortDescription shortDescription = new ShortDescription();
        shortDescription.setId((long) 1);
        shortDescription.setAttribute("shortDescription");
        shortDescriptions.add(shortDescription);
        updatedProduct.setShortDescriptions(shortDescriptions);

        when(productMapper.mapToProduct(product, productDto, productStatusWithdrawn)).thenReturn(updatedProduct);
        // When
        Product retrieved = productService.updateProduct(productDto);
        assertEquals("title", retrieved.getTitle());
        assertEquals(new Long(1), retrieved.getId());
        assertEquals(new BigDecimal(100), retrieved.getPrice());
        assertEquals("image", retrieved.getImageLink());
        assertEquals("description", retrieved.getDescription());
        assertEquals(10, retrieved.getAvailableAmount());
        assertEquals(10, retrieved.getTotalAmount());
    }

    @Test
    public void updateTaskProductSale() {
        // Given
        Product product = new Product();
        product.setId((long) 1);
        ProductStatus productStatus = new ProductStatus("inaccessible", "Product is inaccessible");
        product.setStatus(productStatus);

        ProductEmailReminder productEmailReminder = new ProductEmailReminder();
        productEmailReminder.setId((long) 1);
        productEmailReminder.setEmail("test@test.com");
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productEmailReminder.setProducts(productList);
        when(productDao.findById((long) 1)).thenReturn(product);

        List<ProductEmailReminder> productEmailReminders = new ArrayList<>();
        productEmailReminders.add(productEmailReminder);
        product.setProductEmailReminders(productEmailReminders);

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setStatusCode("sale");

        ProductStatus productStatusSale = new ProductStatus();
        productStatusSale.setId((long) 2);
        productStatusSale.setCode("sale");
        productStatusSale.setName("Product is for sale");
        when(productStatusDao.findProductStatusByCode("sale")).thenReturn(productStatusSale);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setTitle("title");
        updatedProduct.setPrice(new BigDecimal(100));
        updatedProduct.setImageLink("image");
        updatedProduct.setAvailableAmount(10);
        updatedProduct.setTotalAmount(10);
        updatedProduct.setDescription("description");
        List<ShortDescription> shortDescriptions = new ArrayList<>();
        ShortDescription shortDescription = new ShortDescription();
        shortDescription.setId((long) 1);
        shortDescription.setAttribute("shortDescription");
        shortDescriptions.add(shortDescription);
        updatedProduct.setShortDescriptions(shortDescriptions);
        updatedProduct.setStatus(productStatusSale);
        when(productMapper.mapToProduct(product, productDto, productStatusSale)).thenReturn(updatedProduct);
        when(productMapper.mapToProductDtoForMail(product)).thenReturn(new ProductDto());

        // When
        Product retrieved = productService.updateProduct(productDto);
        assertEquals("title", retrieved.getTitle());
        assertEquals(new Long(1), retrieved.getId());
        assertEquals("sale", retrieved.getStatus().getCode());
        assertEquals("Product is for sale", retrieved.getStatus().getName());
        assertEquals(new BigDecimal(100), retrieved.getPrice());
        assertEquals("image", retrieved.getImageLink());
        assertEquals("description", retrieved.getDescription());
        assertEquals(10, retrieved.getAvailableAmount());
        assertEquals(10, retrieved.getTotalAmount());
        assertEquals(0, retrieved.getProductEmailReminders().size());
    }

    @Test
    public void saveProduct() {
        // Given
        ProductDto productDto = createProductDto();
        ProductStatus productStatusSale = productStatusSale();
        productStatusSale.setCode("sale");
        productStatusSale.setName("Product is for sale");
        when(productStatusDao.findProductStatusByCode("sale")).thenReturn(productStatusSale);
        Category category = new Category();
        category.setId((long) 1);
        category.setName("category");
        when(categoryDao.findByName("category")).thenReturn(category);

        Product productSaved  = new Product();
        productSaved.setTitle("title");
        productSaved.setStatus(productStatusSale);
        productSaved.setPrice(new BigDecimal(100));
        productSaved.setImageLink("image");
        productSaved.setDescription("description");
        productSaved.setTotalAmount(10);
        productSaved.setAvailableAmount(10);
        productSaved.setCategory(category);
        when(productMapper.mapToProduct(productDto, productStatusSale, category)).thenReturn(productSaved);

        // When
        Product product = productService.saveProduct(productDto);
        // Then
        assertEquals("title", product.getTitle());
        assertEquals("sale", product.getStatus().getCode());
        assertEquals("Product is for sale", product.getStatus().getName());
        assertEquals(new BigDecimal(100), product.getPrice());
        assertEquals("image", product.getImageLink());
        assertEquals("description", product.getDescription());
        assertEquals(10, product.getTotalAmount());
        assertEquals(10, product.getAvailableAmount());
        assertEquals("category", product.getCategory().getName());
    }

    @Test
    public void getOneProductExits() {
        // Given
        Product product = createProduct();
        when(productDao.findById(productId)).thenReturn(product);
        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setPrice(new BigDecimal(100));
        productDto.setTitle("Computer");
        productDto.setDescription("Super Computer");
        productDto.setTotalAmount(10);
        productDto.setAvailableAmount(2);
        productDto.setStatusCode("sale");
        productDto.setStatusMessage("Product is on Sale");
        productDto.setCountMarks(new BigDecimal(10));
        productDto.setMarksAverage(new BigDecimal(10));
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(new CommentDto());
        productDto.setCommentDtos(commentDtoList);
        when(productMapper.mapToProductDto(product)).thenReturn(productDto);
        // When
        ProductDto retrieved = productService.getOneProduct(productId);
        // Then
        assertEquals(new Long(1), retrieved.getId());
        assertEquals(new BigDecimal(100), retrieved.getPrice());
        assertEquals("Computer", retrieved.getTitle());
        assertEquals("Super Computer", retrieved.getDescription());
        assertEquals(10, retrieved.getTotalAmount());
        assertEquals(2, retrieved.getAvailableAmount());
        assertEquals("sale", retrieved.getStatusCode());
        assertEquals("Product is on Sale", retrieved.getStatusMessage());
        assertEquals(new BigDecimal(10), retrieved.getCountMarks());
        assertEquals(new BigDecimal(10), retrieved.getMarksAverage());
        assertEquals(1, retrieved.getCommentDtos().size());
    }

    @Test
    public void getOneProductNotExist() {
        // Given
        when(productDao.findById(productId)).thenReturn(null);
        // When
        ProductDto retrieved = productService.getOneProduct(productId);
        // Then
        assertEquals(null, retrieved.getId());
    }

    @Test
    public void searchProductExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        when(productDao.findProductContainstTitleWithLetters("product")).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);

        // When
        List<ProductDto> retrieved = productService.searchProduct("product");
        // Then
        assertEquals(3, retrieved.size());
    }

    @Test
    public void searchProductNotExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.findProductContainstTitleWithLetters("product")).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);
        // When
        List<ProductDto> retrieved = productService.searchProduct("product");
        // Then
        assertEquals(0, retrieved.size());
    }

    @Test
    public void filterProductWithPriceExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());
        when(productDao.findProductWithPriceBetween(0, 1000)).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        productDtoList.add(new ProductDto());
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);
        // When
        List<ProductDto> retrieved = productService.filterProductWithPriceBetween(0, 1000);
        // Then
        assertEquals(3, retrieved.size());
    }

    @Test
    public void filterProductWithPriceNotExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.findProductWithPriceBetween(1000, 1001)).thenReturn(productList);
        List<ProductDto> productDtoList = new ArrayList<>();
        when(productMapper.mapToProductDtoList(productList)).thenReturn(productDtoList);
        // When
        List<ProductDto> retrieved = productService.filterProductWithPriceBetween(1000, 1001);
        // Then
        assertEquals(0, retrieved.size());
    }


    @Test
    public void getAllProductTitleExits() {
        // Given
        List<Product> productList = createProductList();
        when(productDao.getProductTitleOnSale()).thenReturn(productList);
        // When
        List<String> productTitle = productService.getAllProductsTitle();
        // Then
        assertEquals(3, productTitle.size());
    }

    @Test
    public void getAllProductTitleNotExist() {
        // Given
        List<Product> productList = new ArrayList<>();
        when(productDao.getProductTitleOnSale()).thenReturn(productList);
        // When
        List<String> productTitle = productService.getAllProductsTitle();
        // Then
        assertEquals(0, productTitle.size());
    }

    @Test
    public void setReminderEmailNotExist() {
        // Given
        ProductEmailReminderDto productEmailReminderDto = new ProductEmailReminderDto();
        productEmailReminderDto.setProductId(productId);
        productEmailReminderDto.setEmail("test@test.com");
        Product product = createProduct();
        when(productDao.findById(productId)).thenReturn(product);
        when(productEmailReminderDao.existsByEmail("test@test.com")).thenReturn(false);
        // When
        productService.setReminder(productEmailReminderDto);
        // Then
        assertEquals(1, product.getProductEmailReminders().size());
    }

    @Test
    public void setReminderEmailExist() {
        // Given
        ProductEmailReminderDto productEmailReminderDto = new ProductEmailReminderDto(productId, "test@test.com");
        Product product = createProduct();
        when(productDao.findById(productId)).thenReturn(product);
        when(productEmailReminderDao.existsByEmail("test@test.com")).thenReturn(true);
        ProductEmailReminder productEmailReminder = new ProductEmailReminder();
        when(productEmailReminderDao.findByEmail("test@test.com")).thenReturn(productEmailReminder);
        // When
        productService.setReminder(productEmailReminderDto);
        // Then
        assertEquals(1, product.getProductEmailReminders().size());
        assertEquals(1, productEmailReminder.getProducts().size());
    }

    @Test
    public void setReminderEmailExistReminderSet() {
        // Given
        ProductEmailReminderDto productEmailReminderDto = new ProductEmailReminderDto(productId, "test@test.com");
        ProductEmailReminder productEmailReminder = new ProductEmailReminder();
        productEmailReminder.setEmail("test@test.com");
        Product product = createProduct();
        product.getProductEmailReminders().add(productEmailReminder);

        when(productDao.findById(productId)).thenReturn(product);
        when(productEmailReminderDao.existsByEmail("test@test.com")).thenReturn(true);

        // When
        productService.setReminder(productEmailReminderDto);
        // Then
        assertEquals(1, product.getProductEmailReminders().size());
        assertEquals(0, productEmailReminder.getProducts().size());
        assertEquals("test@test.com", product.getProductEmailReminders().get(0).getEmail());
    }

    @Test
    public void maxPriceProduct() {
        // Given
        Product product = createProduct();
        when(productDao.getMaxProductPrice()).thenReturn(product);
        // When
        BigDecimal maxPrice = productService.maxPriceProduct();
        // Then
        assertEquals(new BigDecimal(100), maxPrice);
    }

    @Test
    public void markProduct() {
        // Given
        ProductMarkDto productMarkDto = new ProductMarkDto();
        productMarkDto.setMark(2);
        productMarkDto.setLogin("test@test.com");
        Users user = new Users();
        user.setName("tester");
        when(userDao.findByLogin("test@test.com")).thenReturn(user);

        productMarkDto.setProductId(productId);
        Product product = createProduct();
        ProductMark productMark = new ProductMark();
        productMark.setMark(0);
        product.getProductMarks().add(productMark);

        Product productAfterMark = createProduct();
        ProductMark productMarkAfterMark = new ProductMark();
        productMarkAfterMark.setMark(2);
        productAfterMark.getProductMarks().add(productMarkAfterMark);
        when(productDao.findById(productId)).thenReturn(product).thenReturn(productAfterMark);
        // When
        productService.markProduct(productMarkDto);
        // Then
        assertEquals(new BigDecimal(2), productMarkDto.getAverageMarks());
    }

    @Test
    public void markProduct2() {
        // Given
        ProductMarkDto productMarkDto = new ProductMarkDto();
        productMarkDto.setMark(0);
        productMarkDto.setLogin("test@test2.com");
        Users user = new Users();
        user.setName("tester2");
        when(userDao.findByLogin("test@test2.com")).thenReturn(user);

        productMarkDto.setProductId(productId);
        Product product = createProduct();
        ProductMark productMark = new ProductMark();
        productMark.setMark(10);
        product.getProductMarks().add(productMark);

        Product productAfterMark = createProduct();
        ProductMark productMarkAfterMark = new ProductMark();
        productMarkAfterMark.setMark(0);
        productAfterMark.getProductMarks().add(productMarkAfterMark);
        when(productDao.findById(productId)).thenReturn(product).thenReturn(productAfterMark);
        // When
        productService.markProduct(productMarkDto);
        // Then
        assertEquals(new BigDecimal(0), productMarkDto.getAverageMarks());
        assertEquals(new BigDecimal(0), productAfterMark.getSumMarks());
    }

    public ProductStatus productStatusSale() {
        ProductStatus productStatusSale = new ProductStatus();
        productStatusSale.setId((long) 2);
        productStatusSale.setCode("sale");
        productStatusSale.setName("Product is for sale");
        return productStatusSale;
    }

    public ProductDto createProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId((long) 1);
        productDto.setStatusCode("sale");
        productDto.setPrice(new BigDecimal(100));
        productDto.setImageLink("image");
        productDto.setDescription("description");
        productDto.setTotalAmount(10);
        productDto.setTitle("title");
        productDto.setCategory("category");
        return productDto;
    }

    public List<Product> createProductList() {
        Product product1 = createProduct();
        Product product2 = createProduct();
        Product product3 = createProduct();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        return productList;
    }

    public Product createProduct() {
        Product product = new Product();
        product.setId((long) 1);
        product.setPrice(new BigDecimal(100));
        product.setTitle("Computer");
        product.setDescription("Super Computer");
        product.setImageLink("https://image3.mouthshut.com/images/imagesp/925872676s.jpg");
        product.setTotalAmount(10);
        product.setAvailableAmount(2);
        ProductStatus productStatus = new ProductStatus("sale", "Product is on Sale");
        product.setStatus(productStatus);
        product.setSumMarks(new BigDecimal(100));
        product.setCountMarks(new BigDecimal(10));
        product.setAverageMarks(new BigDecimal(10));

        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId((long) 1);
        Users users = new Users();
        users.setLogin("test@test.com");
        comment.setUser(users);
        comment.setCreated(new Date());
        comment.setProduct(product);
        commentList.add(comment);
        product.setComments(commentList);
        return product;
    }

    public Product createProductToRemowe() {
        Product product = new Product();
        product.setId((long) 1);
        product.setPrice(new BigDecimal(100));
        product.setTitle("Computer");
        product.setDescription("Super Computer");
        product.setImageLink("https://image3.mouthshut.com/images/imagesp/925872676s.jpg");
        product.setTotalAmount(10);
        product.setAvailableAmount(2);
        ProductStatus productStatus = new ProductStatus("sale", "Product is on Sale");
        product.setStatus(productStatus);
        product.setSumMarks(new BigDecimal(100));
        product.setCountMarks(new BigDecimal(10));
        product.setAverageMarks(new BigDecimal(10));

        Users users = new Users();
        users.setLogin("test@test.com");
        users.setName("Tester");
        List<ProductBucket> productBuckets = new ArrayList<>();

        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(product);
        Bucket bucket = new Bucket();
        bucket.setId((long) 1);
        bucket.setUser(users);
        productBucket.setBucket(bucket);
        productBuckets.add(productBucket);

        product.setProductBuckets(productBuckets);

        return product;
    }
}