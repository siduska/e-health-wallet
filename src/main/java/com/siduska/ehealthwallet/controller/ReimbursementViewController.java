package com.siduska.ehealthwallet.controller;

import com.siduska.ehealthwallet.service.ReimbursementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/requests")
public class ReimbursementViewController {

    private final ReimbursementService reimbursementService;

    @GetMapping("/pending")
    public String getAllPendingReimbursements(Model model) {
        model.addAttribute("pending", reimbursementService.getAllReimbursementsByStatus("PENDING"));
        return "pending";
    }
}