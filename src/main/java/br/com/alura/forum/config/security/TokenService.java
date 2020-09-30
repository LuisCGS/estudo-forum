package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${forum.jwt.expiration}") // Traz parametros do application.properties
    private String expiration; // traz 86400000 que significa 1 dia em millisegundos

    @Value("${forum.jwt.secret}") // Traz parametros do application.properties
    private String secret; // é uma string aleatória gigantesca

    public String gerarToken(Authentication authenticate) {
        Usuario logado = (Usuario) authenticate.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API do fórum da Alura") // Quem é que (qual sistema) ta gerando esse token
                .setSubject(logado.getId().toString()) // Quem é o usuário ao qual pertence esse token
                .setIssuedAt(hoje) // Qual foi a data de geração do token
                .setExpiration(dataExpiracao) // data de expiração
                .signWith(SignatureAlgorithm.HS256, secret) // vai encriptografar com vc passando o algoritmo de criptografia e a senha
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
