package com.mengxuegu.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mengxuegu.security")
public class SecurityProperties {

    //会将 mengxuegu.security.authentication 下面的值绑定到AuthenticationProperties
    private AuthenticationProperties authentication;

}
