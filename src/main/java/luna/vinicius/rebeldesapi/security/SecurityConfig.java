package luna.vinicius.rebeldesapi.security;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.filter.CustomAuthenticationFilter;
import luna.vinicius.rebeldesapi.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    public String secret;

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), secret);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();

        //Admin Only
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/rebeldes").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/rebeldes/inserir").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/rebeldes/atualizaLocalizacao/**").hasAnyAuthority("ROLE_ADMIN");

        //Rebelde, Admin Only
        http.authorizeRequests().antMatchers("/rebeldes/reportarTraidor/**").hasAnyAuthority("ROLE_REBELDE", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/rebeldes/reportarTraidor/**").hasAnyAuthority("ROLE_REBELDE", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/rebeldes/negociar").hasAnyAuthority("ROLE_REBELDE", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/rebeldesRelatorio/tipoRecurso").hasAnyAuthority("ROLE_REBELDE", "ROLE_ADMIN");

        //Traidor, Rebelde, Admin Only
        http.authorizeRequests().antMatchers("/rebeldesRelatorio/**").hasAnyAuthority("ROLE_TRAIDOR", "ROLE_REBELDE", "ROLE_ADMIN");


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(secret), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
