package com.example.passwordmanager.security.password;

import com.example.passwordmanager.security.securityService.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class PasswordManagerSecurityConfiguration extends WebSecurityConfigurerAdapter {


//    private MyUserDetailsService myUserDetailsService;

//    public PasswordManagerSecurityConfiguration(MyUserDetailsService myUserDetailsService){
//        this.myUserDetailsService=myUserDetailsService;
//    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http.addFilterBefore(
//                new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);
        http.authorizeRequests()
                .antMatchers("/dashboard").hasAuthority("USER")
                .antMatchers("/login", "/register")
                .permitAll().and().formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
        ;



    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }
}
