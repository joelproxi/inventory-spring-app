package com.dealersautocenter.inventory.infrastructure.security.config;

import com.dealersautocenter.inventory.domain.exception.ForbiddenAccessException;
import com.dealersautocenter.inventory.infrastructure.security.RequireRole;
import com.dealersautocenter.inventory.infrastructure.security.TenantContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAuthorizationAspect {

    @Before("@annotation(requireRole)")
    public void checkRole(RequireRole requireRole) {
        String required = requireRole.value();
        String current  = TenantContext.getUserRole();
        if (!required.equalsIgnoreCase(current)) {
            throw new ForbiddenAccessException(
                    "Access denied. Required role: " + required);
        }
    }
}
