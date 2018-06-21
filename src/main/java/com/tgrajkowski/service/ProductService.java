package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.mail.MailType;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.mark.ProductMark;
import com.tgrajkowski.model.product.mark.ProductMarkDto;
import com.tgrajkowski.model.product.reminder.ProductEmailReminder;
import com.tgrajkowski.model.product.reminder.ProductEmailReminderDto;
import com.tgrajkowski.model.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductBucketDao productBucketDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private ProductStatusDao productStatusDao;

    @Autowired
    private ProductEmailReminderDao productEmailReminderDao;

    private ProductMapper productMapper = new ProductMapper();

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductMarkDao productMarkDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ShortDescriptionDao shortDescriptionDao;

    public int checkAvailable(Long id) {
        Optional<Product> product = Optional.ofNullable(productDao.findById(id));
        if (product.isPresent()) {
            return product.get().getAvailableAmount();
        }
        return -1;
    }

    public List<ProductDto> getProducts() {
        List<Product> productList = productDao.getProductOnSaleAndInaccesiableAsc();
        return productMapper.mapToProductDtoList(productList);
    }

    public List<ProductDto> getProductsToEdit() {
        List<Product> products = productDao.findAll();
        return productMapper.mapToProductDtoList(products);
    }

    public void removeProductFromDatabase(Long id) {
        Product product = productDao.findById(id);
        List<ProductBucket> productBuckets = product.getProductBuckets();
        List<ProductBucketPK> productBucketPKS = new ArrayList<>();
        for (ProductBucket productBucket : productBuckets) {
            sendEmailProductWithdrawn(
                    productBucket.getBucket().getUser().getLogin(),
                    product.getTitle(),
                    productBucket.getBucket().getUser().getName());
        }
        for (ProductBucket productBucket : productBuckets) {
            productBucketPKS.add(new ProductBucketPK(productBucket.getProduct().getId(), productBucket.getBucket().getId()));
            Bucket bucket = productBucket.getBucket();
            bucket.getProductBuckets().remove(productBucket);
            bucketDao.save(bucket);
        }
        product.setProductBuckets(new ArrayList<>());
        product.setAvailableAmount(product.getTotalAmount());
        product.setLastModification(new Date());
        productDao.save(product);

        for (ProductBucketPK productBucketPK : productBucketPKS) {
            productBucketDao.delete(productBucketPK);
        }
        product.setToDelete(true);
        productDao.save(product);
    }

    public void deleteProductFromSale() {
        List<Product> productsToDelete = productDao.findByToDelete(true);
        ProductStatus productStatus = productStatusDao.findProductStatusByCode("withdrawn");

        for (Product product : productsToDelete) {
            product.setStatus(productStatus);
            product.setToDelete(false);
            productDao.save(product);
        }
    }

    public void sendEmailProductWithdrawn(String email, String productTitle, String userName) {
        String subject = "Product " + productTitle + " soon unavailable in Computer WebShop";
        String message = "Dear user " + userName + " administrator redirect " + productTitle + " to removing process. Product will be anavaiable until 23:59 this day";
        send(email, subject, message);
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        Product product = productDao.findById(productDto.getId());
        if (productDto.getStatusCode().equals("sale") && product.getStatus().getCode().equals("inaccessible")
                || productDto.getStatusCode().equals("sale") && product.getStatus().getCode().equals("initial")) {
            product = notifyUsers(product);
        }
        ProductStatus productStatus = productStatusDao.findProductStatusByCode(productDto.getStatusCode());
        product = productMapper.mapToProduct(product, productDto, productStatus);
        List<ShortDescription> oldDesc = product.getShortDescriptions();
        shortDescriptionDao.delete(oldDesc);
        product.setShortDescriptions(new ArrayList<>());
        productDao.save(product);
        List<ShortDescription> newDesc = new ArrayList<>();
        for (String desc: productDto.getShortDescription()) {
            if (desc != null) {
                ShortDescription shDesc = new ShortDescription(desc, product);
                shortDescriptionDao.save(shDesc);
            }
        }
        product.setShortDescriptions(newDesc);
        productDao.save(product);
    }


    public Product notifyUsers(Product product) {
        List<ProductEmailReminder> remindersEmpty = new ArrayList<>();
        List<ProductEmailReminder> reminders = product.getProductEmailReminders();
        for (ProductEmailReminder reminder : reminders) {
            sendEmailProductAvailable(reminder.getEmail(), product.getTitle());
            reminder.getProducts().remove(product);
            productEmailReminderDao.save(reminder);
            if (reminder.getProducts().size() == 0) {
                remindersEmpty.add(reminder);
            }
        }
        product.setProductEmailReminders(new ArrayList<>());
        productDao.save(product);

        for (ProductEmailReminder reminder : remindersEmpty) {
            productEmailReminderDao.save(reminder);
            productEmailReminderDao.delete(reminder);
        }
        return product;
    }

    public void sendEmailProductAvailable(String email, String productTitle) {
        String subject = "Product " + productTitle + " available in Computer WebShop";
        String message = "Dear user you set reminder for product " + productTitle + " now it is accessible";
        send(email, subject, message);
    }

    public void send(String email, String subject, String message) {
        Mail mail = new Mail(email, subject);
        mail.setMessage(message);
        mail.setTemplate("product");
        mail.setFragment("information");
        simpleEmailService.sendMail(mail);
    }

    public Product saveProduct(ProductDto productDto) {
        ProductStatus productStatus = productStatusDao.findProductStatusByCode(productDto.getStatusCode());
        Category category = categoryDao.findByName(productDto.getCategory());

        Product product = productMapper.mapToProduct(productDto, productStatus, category);
        for (String des: productDto.getShortDescription()) {
            if (des.length()>5){
                ShortDescription shortDescription = new ShortDescription(des, product);
                shortDescriptionDao.save(shortDescription);
                product.getShortDescriptions().add(shortDescription);
            }
        }
        productDao.save(product);
        return product;
    }

    public ProductDto getOneProduct(Long id) {
        Optional<Product> product = Optional.ofNullable(productDao.findById(id));
        if (product.isPresent()) {
            return productMapper.mapToProductDto(product.get());
        }
        return new ProductDto();
    }

    public List<ProductDto> searchProduct(String title) {
        return productMapper.mapToProductDtoList(productDao.findProductContainstTitleWithLetters(title));
    }

    public List<ProductDto> filterProductWithPriceBetween(int above, int below) {
        return productMapper.mapToProductDtoList(productDao.findProductWithPriceBetween(above, below));
    }

    public List<String> getAllProductsTitle() {
        List<String> productTitle = new ArrayList<>();
        List<Product> products = productDao.getProductTitleOnSale();
        for (Product product : products) {
            productTitle.add(product.getTitle());
        }
        return productTitle;
    }

    public void setReminder(ProductEmailReminderDto productEmailReminderDto) {
        Product product = productDao.findById(productEmailReminderDto.getProductId());
        List<ProductEmailReminder> reminders;
        boolean isSubscirbe = false;
        if (productEmailReminderDao.existsByEmail(productEmailReminderDto.getEmail())) {
            reminders = product.getProductEmailReminders();
            for (ProductEmailReminder reminder : reminders) {
                if (reminder.getEmail().equals(productEmailReminderDto.getEmail())) {
                    isSubscirbe = true;
                }
            }
            if (!isSubscirbe) {
                ProductEmailReminder reminder2 = productEmailReminderDao.findByEmail(productEmailReminderDto.getEmail());
                reminder2.getProducts().add(product);
                product.getProductEmailReminders().add(reminder2);
                productEmailReminderDao.save(reminder2);
            }
        } else {
            ProductEmailReminder productEmailReminder = new ProductEmailReminder(productEmailReminderDto.getEmail());
            productEmailReminder.getProducts().add(product);
            product.getProductEmailReminders().add(productEmailReminder);
            productEmailReminderDao.save(productEmailReminder);
        }
    }

    public BigDecimal maxPriceProduct() {
        BigDecimal maxValue = productDao.getMaxProductPrice().getPrice();
        maxValue = maxValue.setScale(0, RoundingMode.HALF_UP);
        return maxValue;
    }

    public ProductMarkDto markProduct(ProductMarkDto productMarkDto) {
        BigDecimal sumMarks = BigDecimal.ZERO;
        BigDecimal averageMarks;
        BigDecimal countMarks = BigDecimal.ZERO;

        Users user = userDao.findByLogin(productMarkDto.getLogin());
        Product product = productDao.findById(productMarkDto.getProductId());

        ProductMark productMark = new ProductMark(product, user, productMarkDto.getMark());
        productMarkDao.save(productMark);

        product = productDao.findById(productMarkDto.getProductId());
        List<ProductMark> productMarks = product.getProductMarks();
        for (ProductMark productMarkTemp : productMarks) {
            sumMarks = sumMarks.add(new BigDecimal(productMarkTemp.getMark()));
            countMarks = countMarks.add(BigDecimal.ONE);
        }
        averageMarks = sumMarks.divide(countMarks);
        product.setSumMarks(sumMarks);
        product.setCountMarks(countMarks);
        product.setAverageMarks(averageMarks);
        productDao.save(product);

        productMarkDto.setAverageMarks(averageMarks);
        productMarkDto.setCountMarks(countMarks);
        return productMarkDto;
    }
}
