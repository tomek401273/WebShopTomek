package com.tgrajkowski.controller;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.product.order.OrderSearch;
import com.tgrajkowski.model.product.order.ProductsOrder;
import com.tgrajkowski.model.product.order.ProductsOrderDto;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.service.BuyService;
import com.tgrajkowski.service.LocationService;
import com.tgrajkowski.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/buy")
@CrossOrigin("*")
public class BuyController {

    @Autowired
    private BuyService buyService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/buy")
    public Long buyAllProductInBucket(@RequestBody ShippingAddressDto shippingAddressDto) {
        return buyService.buyAllProductInBucket(shippingAddressDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getOrders")
    public List<ProductsOrderDto> getAllProductsOrders(@RequestParam String login) {
        return buyService.getAllProductsOrdersUser(login);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getOrder")
    public ProductsOrderDto getProductsOrder(@RequestParam Long id) {
        return buyService.getProductsOrder(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllOrdersInShop")
    public List<ProductsOrderDto> getAllOrdersInShop() {
        return buyService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/paymentVerification")
    public boolean paymentVerification(@RequestBody OrderStatus orderStatus) {
        return buyService.paymentVerification(orderStatus);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/orderPrepared")
    public boolean orderPrepared(@RequestBody OrderStatus orderStatus) {
        return buyService.orderPrepared(orderStatus);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/sendOrder")
    public boolean sendOrder(@RequestBody OrderStatus orderStatus) {
        return buyService.sendOrder(orderStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchOrderContainsProduct")
    public Set<ProductsOrderDto> searchOrderContainsProduct(@RequestParam String title) {
        return buyService.searchOrderContainsProduct(title);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filterOrderState")
    public List<ProductsOrderDto> filterOrderState(@RequestParam String state) {
        return buyService.filterOrderState(state);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filterOrderDate")
    public List<ProductsOrderDto> filterOrderDate(@RequestParam String dateAfter, @RequestParam String dateBefore) {
        return buyService.filterOrderDate(dateAfter, dateBefore);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filterOrderByUserLogin")
    public List<ProductsOrderDto> filterOrederByUserLogin(@RequestParam String userLogin) {
        return buyService.getAllProductsOrdersUser(userLogin);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserLogin")
    public List<String> getAllUserLogin() {
        return userService.getAllUser();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/orderSearch")
    public List<ProductsOrderDto> orderSearch(@RequestParam String productTitle, @RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String status, @RequestParam String userLogin) {
        OrderSearch orderSearch = new OrderSearch(productTitle, dateFrom, dateTo, status, userLogin);
        return buyService.searchOrders(orderSearch);
    }

}
