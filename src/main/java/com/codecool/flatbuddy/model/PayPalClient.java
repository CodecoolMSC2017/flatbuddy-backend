package com.codecool.flatbuddy.model;

import com.codecool.flatbuddy.service.AdvertisementService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PayPalClient {

    @Value("${paypal.client}")
    private String client;
    @Value("${paypal.secret}")
    private String secret;

    private Integer selectedAdvertisement;

    @Autowired
    private AdvertisementService advertisementService;


    public Map<String, Object> createPayment(String sum, Integer selectedAdvertisement){
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(sum);
        this.selectedAdvertisement = selectedAdvertisement;
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4200/cancel");
        redirectUrls.setReturnUrl("http://localhost:4200/paypaldone");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(client, secret, "sandbox");
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public void completePayment(HttpServletRequest req){
        Payment payment = new Payment();
        payment.setId(req.getParameter("paymentId"));

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("PayerID"));
        try {
            APIContext context = new APIContext(client, secret, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            if(createdPayment!=null){
                /* set premium  */
                advertisementService.setAdToPremium(selectedAdvertisement);


            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
    }
}
