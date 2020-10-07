package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport // habilitar o suporte para o spring pegar os parametros da url informações da ordenação e paginacao e repassar para o spring data
@EnableCaching
@EnableSwagger2

public class ForumApplication
		extends SpringBootServletInitializer /* Habilita a configuração da Servlet 3.0 dentro do servidor, utilizar quando for gerar um WAR */ {


	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ForumApplication.class); // Utilizar apenas quando for gerar um WAR
	}
}
