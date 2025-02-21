package com.mdms.mdms.config;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.mdms.mdms.entites.Supplier;

@Component
@SessionScope
public class LoggedInSupplier {
    private Supplier supplier;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}