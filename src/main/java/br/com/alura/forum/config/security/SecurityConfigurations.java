package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // Habilita o modulo de segurança na aplicação
@Configuration // Diz que no startup da aplicação o Spring irá carregar e ler as infos desta class
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    // Configurações de autorização (URLs, perfis de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topico").permitAll()
                .antMatchers(HttpMethod.GET, "/topico/*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin();
    }

    // Configurações de autenticação (login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    // Configuraões de recursos estaticos (js, CSS, imagens)
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
