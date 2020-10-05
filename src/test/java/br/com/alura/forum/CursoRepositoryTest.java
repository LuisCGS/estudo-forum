package br.com.alura.forum;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.repository.CursoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest // Como é classe para testar repository usamos essa anotação
// Já tem controle de transação
// Da pra injetar uma entityManager específico para teste
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE /*Precisamos utilizar essa anotação para que o Spring não considere que os testes devem sempre ser executados utilizando um banco de dados em memória.*/)
// Diz como vai ser a configuração da sua base para test
@ActiveProfiles("test") // Spring quando rodar a classe de teste força o profile de test
public class CursoRepositoryTest {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
        String nomeCurso = "HTML 5";
        Curso html5 = new Curso();
        html5.setNome(nomeCurso);
        html5.setCategoria("Programação");
        em.persist(html5);
//        cursoRepository.save(html5);

        Curso curso = cursoRepository.findByNome(nomeCurso);
        Assertions.assertNotNull(curso);
        Assertions.assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    public void naoDeveriaCarregarUmCursoCujoNomeNaoEstejaCadastrado() {
        String nomeCurso = "JPA";
        Curso curso = cursoRepository.findByNome(nomeCurso);

        Assertions.assertNull(curso);
    }
}
