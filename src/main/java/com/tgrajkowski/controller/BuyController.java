package com.tgrajkowski.controller;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.product.order.ProductsOrderDto;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.service.BuyService;
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
    BuyService buyService;

    @Autowired
    UserDetailsService userDetailsService;


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
        System.out.println("dateBefore: " + dateBefore);
        return buyService.filterOrderDate(dateAfter, dateBefore);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filterOrderByUserLogin")
    public List<ProductsOrderDto> filterOrederByUserLogin(@RequestParam String userLogin) {
        return buyService.getAllProductsOrdersUser(userLogin);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserLogin")
    public List<String> getAllUserLogin() {
        return buyService.getAllUser();
    }


}
