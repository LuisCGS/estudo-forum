package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // Habilita o modulo de segurança na aplicação
@Configuration // Diz que no startup da aplicação o Spring irá carregar e ler as infos desta class
@Profile("dev") // Diz ao Spring carrega essa classe quando o profile ativo for de dev
public class DevSecurityConfigurations extends WebSecurityConfigurerAdapter {
    // Configurações de autorização (URLs, perfis de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().csrf().disable(); /*Cross Site Request Forgery - Tipo de ataque hacker de aplicações web mas como a validação nossa é via token então é desabilitado */
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/h2-console/**");
    }

}
