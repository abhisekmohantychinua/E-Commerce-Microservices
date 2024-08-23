package dev.abhisek.paymentservice.controller;

import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments/api")
@RequiredArgsConstructor
public class PaymentApiController {
    private final PaymentService service;

    @PostMapping("create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(service.createPayment(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentResponse> getPaymentByPaymentId(@PathVariable String id, @RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(service.getPaymentById(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPaymentByUserId(@RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(service.getPaymentsByUserId(userId));
    }

//    @PatchMapping("{id}")
//    public ResponseEntity<PaymentResponse> updatePaymentType(@RequestParam String paymentType, @PathVariable String id, @RequestHeader("user_id") String userId) {
//        return ResponseEntity.ok(service.updatePaymentType(paymentType, id, userId));
//    }

//    @PutMapping("{id}")
//    public ResponseEntity<PaymentResponse> updatePaymentAmount(@PathVariable String id, @RequestParam Double amount) {
//        return ResponseEntity.ok(service.updatePaymentAmount(id, amount));
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        service.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
