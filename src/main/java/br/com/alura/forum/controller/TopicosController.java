package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
public class TopicosController {
    @Autowired //injeção de dependencia
    private TopicoRepository topicoRepository;

//    @GetMapping("/topicos/{nomeCurso}")
//    public List<TopicoDto> lista(@PathVariable(value = "nomeCurso", required = false) final String nomeCurso){

//    http://localhost:8080/topico?nomeCurso=abc
//    @GetMapping()
//    public List<TopicoDto> lista(String nomeCurso){}

    @RequestMapping({"/lista/{nomeCurso}", "/lista"})
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
}