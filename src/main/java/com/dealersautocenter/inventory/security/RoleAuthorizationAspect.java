package com.dealersautocenter.inventory.security;

import com.dealersautocenter.inventory.exception.ForbiddenAccessException;
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
