package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.mail.Mail;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketMapper;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import com.tgrajkowski.model.product.reminder.Reminder;
import com.tgrajkowski.model.product.reminder.ProductEmailReminderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductBucketDao productBucketDao;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private ProductStatusDao productStatusDao;

    @Autowired
    private ProductEmailReminderDao productEmailReminderDao;

    ProductMapper mapper = new ProductMapper();

    ProductBucketMapper productBucketMapper = new ProductBucketMapper();

    public int checkAvailable(Long id) {
        Product product = productDao.findById(id);
        return product.getAvailableAmount();
    }

    public List<ProductDto> getProducts() {
        ProductStatus productStatus = productStatusDao.findProductStatusByCode("sale");
        List<Product> products = productDao.findByStatusId(productStatus.getId());
        ProductStatus productStatusInAccessible = productStatusDao.findProductStatusByCode("inaccessible");
        List<Product> productsInAccessible = productDao.findByStatusId(productStatusInAccessible.getId());
        products.addAll(productsInAccessible);
        List<ProductDto> productDtos = mapper.mapToProductDtoList(products);
        Collections.sort(productDtos, Comparator.comparing(ProductDto::getTitle));
        return productDtos;
    }

    public List<ProductDto> getProductsToEdit() {
        List<Product> products = productDao.findAll();
        return mapper.mapToProductDtoList(products);
    }

    public void removeProductFromDatabase(ProductDto productDto) throws InterruptedException {
        Product product = productDao.findById(productDto.getId());
        List<ProductBucket> productBuckets = product.getProductBuckets();
        List<ProductBucketPK> productBucketPKS = new ArrayList<>();

        for (ProductBucket productBucket : productBuckets) {
            String userLogin = productBucket.getBucket().getUser().getLogin();
            String subject = "Product " + product.getTitle() + " soon unavailable in Computer WebShop";
            String message = "Dear user " + productBucket.getBucket().getUser().getName() + " administrator redirect " + product.getTitle() + " to removing process. Product will be anavaiable until 23:59 this day";
            simpleEmailService.send(new Mail(
                    userLogin,
                    subject,
                    message
            ));
        }


        Date date = new Date();
        System.out.println(date);
        Date date2 = calculateDateTomorow();
        System.out.println(date2);
        Long futureDate = date2.getTime();
        Long nowDate = System.currentTimeMillis();
        Long calulatedDate = futureDate - nowDate;

        convertMiliscendToNormalTime(calulatedDate);
        Thread.sleep(calulatedDate);
        System.out.println("Removing DATA");


        for (ProductBucket productBucket : productBuckets) {
            productBucketPKS.add(new ProductBucketPK(productBucket.getProduct().getId(), productBucket.getBucket().getId()));
            Bucket bucket = productBucket.getBucket();
            bucket.getProductBuckets().remove(productBucket);
            bucketDao.save(bucket);
        }
        product.setProductBuckets(new ArrayList<>());
        product.setAvailableAmount(product.getTotalAmount());
        productDao.save(product);

        for (ProductBucketPK productBucketPK : productBucketPKS) {
            productBucketDao.delete(productBucketPK);
        }
        ProductStatus productStatus = productStatusDao.findProductStatusByCode("withdrawn");
        product.setStatus(productStatus);
        productDao.save(product);


//        productDao.delete(product);
    }

    public Date calculateDateTomorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public void convertMiliscendToNormalTime(Long milisec) {
        System.out.println("milisec: " + milisec);
        Long sec = milisec / 1000;
        System.out.println("sec: " + sec);
        Long min = sec / 60;
        Long hsec = sec % 60;
        System.out.println(" min: " + min);
        Long h = min / 60;
        Long hmin = min % 60;
        System.out.println("h: " + h + "; min: " + hmin + "; sec: " + hsec);
    }

    /// skorzystaj z mapera
    public void updateProduct(ProductDto productDto) {
        boolean isReminderEmpty= false;
        Reminder reminderEmpty = new Reminder();
        Product product = productDao.findById(productDto.getId());
        if (productDto.getStatusCode().equals("sale") && product.getStatus().getCode().equals("inaccessible")) {
            System.out.println("Notifiy users that product is available now");
            System.out.println("remove emails form database");
            List<Reminder> reminders = product.getProductEmailReminders();
            for (Reminder reminder : reminders) {
//               reminder.setProducts(new ArrayList<>());
                reminder.getProducts().remove(product);
                productEmailReminderDao.save(reminder);

                if (reminder.getProducts().size() == 0) {
                    System.out.println("SEt isReminderEmpty true");
                    reminderEmpty=reminder;
                   isReminderEmpty=true;
                }

            }
            product.setProductEmailReminders(new ArrayList<>());
            productDao.save(product);
        }

        if (isReminderEmpty) {
            System.out.println("THIS EMAIL HAS NO PRODUCT TO SUB");
            reminderEmpty.setProducts(null);
            System.out.println(reminderEmpty.toString());
            productEmailReminderDao.save(reminderEmpty);
            productEmailReminderDao.delete(reminderEmpty);
            System.out.println("Remoivng succesfull!!!");

        }

        product.setPrice(productDto.getPrice());
        product.setImageLink(productDto.getImageLink());
        product.setDescription(productDto.getDescription());
        product.setAvailableAmount(productDto.getTotalAmount());
        product.setTotalAmount(productDto.getTotalAmount());
        product.setTitle(productDto.getTitle());
        ProductStatus productStatus = productStatusDao.findProductStatusByCode(productDto.getStatusCode());
        product.setStatus(productStatus);
        productDao.save(product);
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        return productDao.save(product);
    }

    public ProductDto getOneProduct(Long id) {
        ProductDto productDto = mapper.mapToProductDto(productDao.findById(id));
        return productDto;
    }

    public List<ProductDto> searchProduct(String title) {

        return mapper.mapToProductDtoList(productDao.findProductContainstTitleWithLetters(title));
    }

    public List<ProductDto> filterProductWithPriceBetween(FilterPrice filterPrice) {
        return mapper.mapToProductDtoList(productDao.findProductWithPriceBetween(filterPrice.getAbove(), filterPrice.getBelow()));
    }

    public List<String> getAllProductsTitle() {
        List<String> productsTitle = new ArrayList<>();
        List<Product> products = productDao.findAll();
        for (Product product : products) {
            productsTitle.add(product.getTitle());
        }
        return productsTitle;
    }

    public boolean setReminder(ProductEmailReminderDto productEmailReminderDto) {
        Product product = productDao.findById(productEmailReminderDto.getProductId());
        System.out.println("productTitle: " + product.getTitle());

        boolean exit = productEmailReminderDao.existsByEmail(productEmailReminderDto.getEmail());
        if (exit) {
            System.out.println("This email exit in database");
            boolean isSubscirbe = false;
            List<Reminder> reminders = product.getProductEmailReminders();

            if (reminders.size() > 0) {
                System.out.println("New email to set: " + productEmailReminderDto.getEmail());
                System.out.println("Subscirbent user");
                for (Reminder reminder : reminders) {
                    System.out.println("subEmail: " + reminder.getEmail());
                    if (reminder.getEmail().equals(productEmailReminderDto.getEmail())) {
                        isSubscirbe = true;
                    }
                }
            }

            if (!isSubscirbe) {
                System.out.println("this user not yet subscribe this procuct. Set sub");
                Reminder reminder = productEmailReminderDao.findByEmail(productEmailReminderDto.getEmail());
                reminder.getProducts().add(product);
                product.getProductEmailReminders().add(reminder);
                productEmailReminderDao.save(reminder);
            } else {
                System.out.println("This user yet subscribe. Do nothing ");
            }

        } else {
            Reminder productEmailReminder = new Reminder(productEmailReminderDto.getEmail());
            productEmailReminder.getProducts().add(product);
            product.getProductEmailReminders().add(productEmailReminder);
            productEmailReminderDao.save(productEmailReminder);
        }

        List<Reminder> reminders = product.getProductEmailReminders();
        for (Reminder reminder : reminders) {
            System.out.println(reminder.getEmail());
        }


//        productDao.save(product);
        return true;
    }
}
