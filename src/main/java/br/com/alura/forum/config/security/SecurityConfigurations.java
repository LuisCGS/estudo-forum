package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // Habilita o modulo de segurança na aplicação
@Configuration // Diz que no startup da aplicação o Spring irá carregar e ler as infos desta class
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    @Autowired
    private AutenticacaoService autenticacaoService;


    // Configurações de autorização (URLs, perfis de acesso)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topico").permitAll()
                .antMatchers(HttpMethod.GET, "/topico/*").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                //.and().formLogin(); Cria uma sessão, e não é a ideia quando se usa API, deve ser stateless
                .and().csrf().disable() /*Cross Site Request Forgery - Tipo de ataque hacker de aplicações web mas como a validação nossa é via token então é desabilitado */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Avisa pro SpringSecurity que não é pra criar uma sessão
    }

    // Configurações de autenticação (login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configuraões de recursos estaticos (js, CSS, imagens)
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
