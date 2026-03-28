package com.dealersautocenter.inventory.infrastructure.security.adapter;

import com.dealersautocenter.inventory.application.port.out.TenantPort;
import com.dealersautocenter.inventory.infrastructure.security.TenantContext;
import org.springframework.stereotype.Component;

@Component
public class TenantContextAdapter implements TenantPort {

    @Override
    public String getTenantId() {
        return TenantContext.getTenantId();
    }

    @Override
    public String getUserRole() {
        return TenantContext.getUserRole();
    }
}
