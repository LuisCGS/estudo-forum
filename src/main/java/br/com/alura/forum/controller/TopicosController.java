package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class TopicosController {
    @Autowired //injeção de dependencia
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

//    @GetMapping("/topicos/{nomeCurso}")
//    public List<TopicoDto> lista(@PathVariable(value = "nomeCurso", required = false) final String nomeCurso){

//    http://localhost:8080/topico?nomeCurso=abc
//    @GetMapping()
//    public List<TopicoDto> lista(String nomeCurso){}

    @GetMapping({"/lista/{nomeCurso}", "/lista"})
    public List<TopicoDto> lista(@PathVariable(value = "nomeCurso", required = false) Optional<String> nomeCurso){
        System.out.println(nomeCurso);
//        Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring","Programação"));
//        return TopicoDto.converter(Arrays.asList(topico, topico));

        if (nomeCurso.isEmpty()) {
            return TopicoDto.converter(topicoRepository.findAll());
        } else {
            return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso.get()));
        }
    }

    //Deve devolver 201 que significa SUCESSO / CRIADO
    @PostMapping("/cadastrar")
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));//create devolve o 201
    }

    @GetMapping("/{id}")
    public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.getOne(id);

        return new DetalhesDoTopicoDto(topico);
    }
}