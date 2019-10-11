package com.example.kotlinbootrest.config

import com.example.kotlinbootrest.controller.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@PropertySource(value = [
    "classpath:application.properties",
    "classpath:db.properties",
    "classpath:log.properties"
])
@EnableWebSecurity
open class BasicConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var authProvider: AuthProvider

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(Api.SIGN_UP).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()

        http.headers().frameOptions().disable() // hack for H2 console
    }

}
