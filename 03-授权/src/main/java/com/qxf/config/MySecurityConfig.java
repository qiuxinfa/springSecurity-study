package com.qxf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Auther: qiuxinfa
 * @Date: 2020/4/11
 * @Description: com.qxf.config
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userServiceImpl;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //第一次就打开，然它创建表，一次就够了FilterSecurityInterceptor
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //允许基于使用HttpServletRequest限制访问
                .antMatchers(HttpMethod.GET,"/user/*").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/user/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/user/*").hasRole("ADMIN")
                .antMatchers("/admin/*").hasRole("ADMIN")
                //有3中方式可以实现动态权限控制：（1）扩展access()的SpEL表达式（2）自定义AccessDecisionManager
                // （3）自定义Filter：MyFilterSecurityInterceptor
                //（1）扩展access()的SpEL表达式：自定义授权逻辑myAuthService是自定义的类，canAccess是它的方法
//                .anyRequest().access("@myAuthService.canAccess(request,authentication)")
                //（2）自定义AccessDecisionManager
//                .withObjectPostProcessor(new MyObjectPostProcessor())
                .anyRequest().authenticated()
                .and()
                //表单登录
                .formLogin()
                //登录页面和处理接口
                .loginPage("/login.html")
                //认证成功后的处理器
                .successHandler(myAuthenticationSuccessHandler)
                //认证失败后的处理器
                .failureHandler(myAuthenticationFailureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                //记住我有效时间为7天
                .tokenValiditySeconds(60*60*24*7)
                .userDetailsService(userServiceImpl)
                .and()
                //关闭跨站请求伪造的防护，这里是为了前期开发方便
                .csrf().disable()
                //访问被拒绝处理器
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }


    private class MyObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
            fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
            fsi.setAccessDecisionManager(myAccessDecisionManager);
            return fsi;
        }

    }
}
