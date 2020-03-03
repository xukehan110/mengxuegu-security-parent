package com.mengxuegu.security.config;

import com.mengxuegu.security.authertication.code.ImageCodeValidateFilter;
import com.mengxuegu.security.authertication.mobilefilter.MobileAuthenticationConfig;
import com.mengxuegu.security.authertication.mobilefilter.MobileValidateFilter;
import com.mengxuegu.security.authorize.AuthorizeConfigurerManager;
import com.mengxuegu.security.properties.SecurityProperties;
import com.mengxuegu.security.session.CostomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * alt + 导包
 * ctrl + o 选择覆盖方法
 */
@Configuration
@EnableWebSecurity // 开启springSecurity过滤器链 filter
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启注解方法级别权限控制
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler costomAuthenticationSuccessHander;

    @Autowired
    private AuthenticationFailureHandler costomAuthenticationFailureHander;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    //校验手机验证码
    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    //校验手机号是否存在，手机号认证
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private AuthorizeConfigurerManager authorizeConfigurerManager;

    @Autowired
    private CostomLogoutHandler costomLogoutHandler;

    @Autowired
    private SessionRegistry sessionRegistry;
    /**
     * 同一个用户session数量超过指定数值之后，会调用这个实现类
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * 对存储的密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        //BCryptPasswordEncoder加密方式是 密码+随机值 在加密
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * 1.认证信息（用户名、密码）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //此方法通过内存方式存储用户信息
//        String password = passwordEncoder().encode("1234");
//        auth.inMemoryAuthentication()
//                .withUser("mengxuegu")
//                .password(password)
//                .authorities("ADMIN");
        //通过查询数据库
        auth.userDetailsService(customUserDetailsService);
    }

    /**
     * 记住我功能
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //开始是创建表使用，创建记住我功能表
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    /**
     * 资源权限配置：
     * 1.被拦截的资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      http.httpBasic()  //采用 httpBasic认证方式
        http.addFilterBefore(mobileValidateFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(securityProperties.getAuthentication().getLoginPage()) //表单登录方式
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) //登录表单提交处理url,默认是/login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) // 默认 username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter())  // 默认 password
                .successHandler(costomAuthenticationSuccessHander)
                .failureHandler(costomAuthenticationFailureHander)
//                .and()
//                .authorizeRequests() //授权请求（即身份认证）
//                .antMatchers(securityProperties.getAuthentication().getLoginPage(),
//                        securityProperties.getAuthentication().getImageCodeUrl(),
//                        securityProperties.getAuthentication().getMobileCodeUrl(),
//                        securityProperties.getAuthentication().getMobilePage()).permitAll()
//
//                //应用的权限控制
//                .antMatchers("/user").hasAuthority("sys:user")
//                .antMatchers(HttpMethod.GET,"/role").hasAuthority("sys:role")
//                .antMatchers(HttpMethod.GET,"/permission")
//                .access("hasAuthority('sys:permission') or hasAnyRole('ADMIN','ROOT')")
//
//
//                .anyRequest().authenticated()//所有访问该应用的http请求都需要通过身份认证才可以访问
                .and()
                .rememberMe()
                .tokenRepository(jdbcTokenRepository())//保存登录信息
                .tokenValiditySeconds(securityProperties.getAuthentication().getTokenValiditySeconds())
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)//当session失效后的处理类
                .maximumSessions(1)
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
//                .maxSessionsPreventsLogin(true)//限制一个用户只能在一台电脑上登录。
                .sessionRegistry(sessionRegistry)
                .and().and()
                .logout()
                .addLogoutHandler(costomLogoutHandler)
        ;
        http.csrf().disable(); //关闭跨站 请求伪造
        //将手机验证添加至过滤器链上
        http.apply(mobileAuthenticationConfig);

        //将所有的授权配置统一的管理起来
        authorizeConfigurerManager.configure(http.authorizeRequests());
    }




    /**
     * 针对静态资源放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
