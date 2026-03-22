package com.example.studentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                        @RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String confirmPassword,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        try {
            authService.register(username, email, password, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! Please login.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            authService.findByEmail(email);
            redirectAttributes.addFlashAttribute("successMessage", 
                "If an account with this email exists, you will receive a password reset link.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Email not found");
            return "forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam(required = false) String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                               @RequestParam String newPassword,
                               @RequestParam String confirmPassword,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            if (!newPassword.equals(confirmPassword)) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            authService.resetPassword(email, newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password reset successfully! Please login with your new password.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "reset-password";
        }
    }
}
