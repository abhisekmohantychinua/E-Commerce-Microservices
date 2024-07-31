package dev.abhisek.paymentservice.controller;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.dto.UserProfileResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import dev.abhisek.paymentservice.entity.PaymentDetails;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.UserService;
import jakarta.servlet.http.HttpSession;
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
    public String processPayment(
            @PathVariable String id,
            @AuthenticationPrincipal UserResponse auth,
            @RequestParam String paymentType,
            @RequestParam(required = false) String bankCode,
            @RequestParam(required = false) String cardNumber,
            @RequestParam(required = false) String cardHolderName,
            @RequestParam(required = false) String expirationDate,
            @RequestParam(required = false) String cvv,
            @RequestParam(required = false) String cardType,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zip,
            @RequestParam(required = false) String phone,
            RedirectAttributes reAttr
    ) {
        PaymentDetails details = service.validateCredentials(id, auth, paymentType, bankCode, cardNumber, cardHolderName, expirationDate, cvv, cardType, address, city, zip, phone);
        reAttr.addFlashAttribute("details", details);
        reAttr.addFlashAttribute("id", id);
        return "redirect:process";
    }

    @GetMapping("process")
    public String paymentProcess(@AuthenticationPrincipal UserResponse auth, Model model, HttpSession session) {
        PaymentDetails paymentDetails = (PaymentDetails) model.getAttribute("details");
        String id = (String) model.getAttribute("id");
        session.setAttribute("details", paymentDetails);
        session.setAttribute("id", id);
        return "private/process";
    }

    @PostMapping("process")
    public String paymentProcess(@AuthenticationPrincipal UserResponse auth, HttpSession session, RedirectAttributes reAttr, @RequestParam Boolean status) {
        if (!status) {
            return "redirect:complete?status=failed";
        }
        PaymentDetails details = (PaymentDetails) session.getAttribute("details");
        String id = (String) session.getAttribute("id");
        session.removeAttribute("details");
        session.removeAttribute("id");
        reAttr.addFlashAttribute("id", id);
        reAttr.addFlashAttribute("details", details);
        return "redirect:complete?status=success";
    }

    @GetMapping("complete")
    public String paymentComplete(@RequestParam String status, Model model, @AuthenticationPrincipal UserResponse auth) {
        if (status.equalsIgnoreCase("success")) {
            PaymentDetails paymentDetails = (PaymentDetails) model.getAttribute("details");
            String id = (String) model.getAttribute("id");
            service.completePayment(id, auth.getId(), paymentDetails);
            return "redirect:dashboard?status=success";
        }
        return "redirect:dashboard?status=failed";
    }


}
