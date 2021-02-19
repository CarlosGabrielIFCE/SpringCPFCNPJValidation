package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class WebSecurityConfiguration {

    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/**")
                .authorizeRequests().and()
                .httpBasic()
                .and()
                .authorizeRequests().anyRequest().authenticated().and().cors();
    }
}
