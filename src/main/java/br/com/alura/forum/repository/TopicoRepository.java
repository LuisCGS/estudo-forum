package br.com.alura.forum.repository;

import br.com.alura.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long/* tipo do id da classe topico*/>  {

    //List<Topico> findByTitulo(String nomeCurso);
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);//curso é o relacionamento e nome é o atributo de curso
    // Quando o atributo da classe conflitar com um relacionamento e atributo utilizar o _ pra mostrar que sera de uma classe o relacionamento

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = : nomeCurso")
    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
