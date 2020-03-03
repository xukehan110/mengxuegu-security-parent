package com.mengxuegu.security.properties;

import com.mengxuegu.security.emun.LoginResponseType;
import lombok.Data;

@Data
public class AuthenticationProperties {

    private String loginPage = "/login/page";

    private String loginProcessingUrl = "/login/form";

    private String usernameParameter = "name";

    private String passwordParameter = "pwd";

    private String[] staticPaths = {"/dist/**","/modules/**","/plugins/**"};

    private LoginResponseType loginType;

    private String imageCodeUrl;

    private String mobileCodeUrl;

    private String mobilePage;

    private Integer tokenValiditySeconds;

}
