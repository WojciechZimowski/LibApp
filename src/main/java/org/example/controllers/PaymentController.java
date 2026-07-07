package org.example.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.models.Order;
import org.example.services.OrderServiceInterface;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final OrderServiceInterface orderService;

    public PaymentController(OrderServiceInterface orderService,@Value("${stripe.api.key}") String stripeApiKey) {
        this.orderService = orderService;
        Stripe.apiKey = stripeApiKey;
    }
    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<Map<String,String>> checkout(@PathVariable String orderId) {
        Order order = orderService.findOrder(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        try{
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3067/api/payments/success?orderId=" + orderId)
                    .setCancelUrl("http://localhost:3067/api/payments/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("pln")
                                            .setUnitAmount((long) (order.getTotalPrice() * 100)) // Cena w groszach
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Zamówienie nr: " + order.getId())
                                                            .build()).build()
                    ).build()).build();
            Session session = Session.create(params);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("paymentUrl", session.getUrl());

            return ResponseEntity.ok(responseData);
        }catch (StripeException e ){
            throw new RuntimeException("Błąd podczas generowania płatności",e);
        }
    }
    @GetMapping("/success")
    public ResponseEntity<String> success(@RequestParam String orderId) {
        orderService.updateOrder(orderId,"PAID");
        return ResponseEntity.ok("Płatność udana");
    }
    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.ok("Płatność anulowana");
    }
}
