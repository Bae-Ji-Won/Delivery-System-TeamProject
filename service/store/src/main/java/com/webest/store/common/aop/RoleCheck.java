package com.webest.store.common.aop;

import com.webest.web.common.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메소드에 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지
public @interface RoleCheck {
    UserRole requiredRole();
}