package com.mdms.mdms.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mdms.mdms.config.LoggedInSupplier;

import com.mdms.mdms.dto.SupplierDetailsDTO;
import com.mdms.mdms.entites.Supplier;
import com.mdms.mdms.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private LoggedInSupplier loggedInSupplier;

    @Autowired
    private SupplierService supplierService;


    @RequestMapping("/dashboard")
    public String dashboard() {
        Supplier supplier = loggedInSupplier.getSupplier();
        System.out.println("Supplier from bean: " + supplier);
        return "supplier/dashboard";
    }

    @GetMapping("/view-all-mealRequests")
    public String viewAllMealRequestsToSupplier(Model model) {
        SupplierDetailsDTO supplierDetails = supplierService.getSupplierDetails(loggedInSupplier.getSupplier().getSupplierId());
        model.addAttribute("supplierDetails", supplierDetails);
        return "supplier/view_all_mealRequests";
    }

    
    

}
