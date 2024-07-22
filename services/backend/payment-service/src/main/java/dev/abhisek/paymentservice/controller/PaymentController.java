package dev.abhisek.paymentservice.controller;

import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.dto.UserProfileResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
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
        model.addAttribute("profile", Base64.getEncoder().encodeToString(user.profile()));// todo - to be fix in FIX-IMAGE
        return "private/dashboard";
    }

    @GetMapping("{id}")
    public String paymentPage(@PathVariable String id, @AuthenticationPrincipal UserResponse auth, Model model) {
        PaymentResponse payment = service.getPaymentById(id, auth.getId());
        model.addAttribute("payment", payment);
        return "private/payment";
    }

    @PostMapping("{id}")
    public String paymentComplete(@PathVariable String id, @AuthenticationPrincipal UserResponse auth, RedirectAttributes reAttr) {
        service.completePayment(id, auth.getId());
        reAttr.addFlashAttribute("paymentSuccessId", id);
        return "redirect:dashboard";
    }

}
