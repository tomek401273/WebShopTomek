package com.tgrajkowski.controller;

import com.tgrajkowski.model.PaymentDto;
import com.tgrajkowski.model.product.order.ProductsOrderDto;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buy")
@CrossOrigin("*")
public class BuyController {

    @Autowired
    BuyService buyService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void simpleTest() {
        buyService.simpleTest();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/buy")
    public void buyAllProductInBucket(@RequestBody ShippingAddressDto shippingAddressDto) {
        buyService.buyAllProductInBucket(shippingAddressDto);
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
    public List<ProductsOrderDto> getAllOrdersInShop(){
        return buyService.getAllOrders();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "paymentVerification")
    public boolean paymentVerification(@RequestBody PaymentDto paymentDto) {
        return buyService.paymentVerification(paymentDto);
    }


}
