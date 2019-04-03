package com.apigateway.project.config;

import com.apigateway.project.security.OpenIdConnectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**Classe che setta la security dell'apiGateway */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private OAuth2RestTemplate restTemplate;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**");
        }

        /**Imposta il Filter che setto con la classe OpenIdConnectFilter che effettua la chiamata
         * di autenticazione al server di google
         */
        @Bean
        public OpenIdConnectFilter myFilter() {
            final OpenIdConnectFilter filter = new OpenIdConnectFilter("/google-login");
            filter.setRestTemplate(restTemplate);
            return filter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off

            http.logout()
                    .logoutSuccessUrl("/logout")
                    .and()

                    .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                    .addFilterAfter(myFilter(), OAuth2ClientContextFilter.class)
                    .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/google-login"))

                    .and()
                    .authorizeRequests()
                    .antMatchers("/home", "/revokeUserConsent").hasAnyRole("ADMIN", "USER")
                    .anyRequest().authenticated()

                    /**Gestisco le eccezioni reindirizzando l'utente a pagine di errore custom */
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403");


            // @formatter:on
        }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}