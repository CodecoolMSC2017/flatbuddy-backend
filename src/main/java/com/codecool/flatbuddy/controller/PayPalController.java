package com.codecool.flatbuddy.controller;


import com.codecool.flatbuddy.model.PayPalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {

    private final PayPalClient payPalClient;
    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("rentId") Integer rentId){
        return payPalClient.createPayment("1500",rentId);
    }

    @PostMapping(value = "/complete/payment")
    public void completePayment(HttpServletRequest request){
        payPalClient.completePayment(request);
    }

}

