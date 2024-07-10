package com.naru.mfa.security.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface AdminAuthorize {

}
