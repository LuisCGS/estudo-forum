package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
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
//    @GetMapping({"/lista/{nomeCurso}", "/lista"})
//    public List<TopicoDto> lista(@PathVariable(value = "nomeCurso", required = false) Optional<String> nomeCurso){
    @GetMapping()
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                @RequestParam int pagina, @RequestParam int quantidade, @RequestParam() String campoOrdenacao) {
//        Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring","Programação"));
//        return TopicoDto.converter(Arrays.asList(topico, topico));

        Pageable paginacao = PageRequest.of(pagina, quantidade, Sort.Direction.DESC, campoOrdenacao);

        if (nomeCurso == null) {
            return TopicoDto.converter(topicoRepository.findAll(paginacao));
        } else {
            return TopicoDto.converter(topicoRepository.findByCurso_Nome(nomeCurso, paginacao));
        }
    }

    //Deve devolver 201 que significa SUCESSO / CRIADO
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));//create devolve o 201
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid AtualizacaoTopicoForm topicoForm) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return ResponseEntity.ok(new TopicoDto(topicoForm.atualizar(topico.get())));
        }


        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}