package dev.abhisek.paymentservice.controller;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.dto.UserProfileResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import dev.abhisek.paymentservice.entity.PaymentDetails;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    private final UserService userService;

    @GetMapping("dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserResponse auth) {
        UserProfileResponse user = userService.getUserProfile(auth.getId());
        List<PaymentResponse> payments = service.getPaymentsByUserId(auth.getId());
        model.addAttribute("user", user);
        model.addAttribute("payments", payments);
        return "private/dashboard";
    }

    @GetMapping("{id}")
    public String paymentPage(@PathVariable String id,
                              @AuthenticationPrincipal UserResponse auth,
                              Model model,
                              @RequestParam(required = false) String paymentType) {
        if (paymentType != null)
            service.updatePaymentType(paymentType, id, auth.getId());
        PaymentResponse payment = service.getPaymentById(id, auth.getId());
        OrderResponse order = service.getOrderByPaymentId(id, auth.getId());
        model.addAttribute("payment", payment);
        model.addAttribute("order", order);
        return "private/payment";
    }

    @PostMapping("{id}")
    public <T extends PaymentDetails> String paymentComplete(@PathVariable String id, @AuthenticationPrincipal UserResponse auth, RedirectAttributes reAttr, @ModelAttribute T details) {
        service.completePayment(id, auth.getId(), details);
        reAttr.addFlashAttribute("paymentSuccessId", id);
        return "redirect:dashboard";
    }

}
