package br.com.alura.forum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

//@WebMvcTest // Teste da camada Controller, o Spring carrega no context tudo que tem haver com a camada Controller
@SpringBootTest // Carrega tudo que também é relacionado diferente do WebMvcTest
@AutoConfigureMockMvc // Necessário para injetar o MockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerTest {
    @Autowired
    private MockMvc mockMvc; // Classe utilitaria do spring

    @Test
    public void deveriaDevolver400CasoDadosDeAutenticacaoEstejamIncorretos() throws Exception {
        URI uri = new URI("/auth");
        String json =   "{" +
                            "\"email\" : \"invalido@email.com\"," +
                            "\"senha\" : \"123456\"" +
                        "}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
}
